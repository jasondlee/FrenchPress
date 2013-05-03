#!/bin/bash

SERVER=glassfish
SERVER_DIR=glassfish4
JAR_DIR=~/Downloads/jars
DB_VENDOR=pgsql
DB_USER=frenchpress
DB_NAME=frenchpress
DB_HOST=localhost
DB_PASS=fp

if [ -e frenchpress.properties ] ; then
    source frenchpress.properties
fi

function startServer() {
    if [ "$SERVER" == "glassfish" ] ; then
        $SERVER_DIR/bin/asadmin start-domain --debug
    elif [ "$SERVER" == "tomee" ] ; then
        export JPDA_ADDRESS=9009
        $SERVER_DIR/bin/catalina.sh jpda start
    fi
}

function stopServer() {
    if [ "$SERVER" == "glassfish" ] ; then
        $SERVER_DIR/bin/asadmin stop-domain
    elif [ "$SERVER" == "tomee" ] ; then
        $SERVER_DIR/bin/catalina.sh stop
    fi
}

function dropTables() {
    SQL="drop table if exists attachments cascade; \
        drop table if exists comments cascade; \
        drop table if exists mediaitems cascade; \
        drop table if exists pages cascade; \
        drop table if exists posts cascade; \
        drop table if exists sequence cascade; \
        drop table if exists settings cascade; \
        drop table if exists users cascade;"
    if [ "$DB_VENDOR" == "pgsql" ] ; then
        psql -U $DB_USER -h $DB_HOST $DB_NAME -c "$SQL"
    elif [ "$DB_VENDOR" == "mysql" ] ; then
        echo mysql
        #mysql -u frenchpress -p frenchpress  << EOL
    fi
}

function deploy() {
    mvn -Dmaven.test.skip=true package
    if [ $? -eq 0 ] ; then
        if [ "$SERVER" == "glassfish" ] ; then
            $SERVER_DIR/bin/asadmin deploy --force target/frenchpress*/
        elif [ "$SERVER" == "tomee" ] ; then
            cp target/frenchpress*war $SERVER_DIR/webapps/frenchpress.war
        fi
    fi
}

function uninstall() {
    if [ "$SERVER" == "glassfish" ] ; then
        $SERVER_DIR/bin/asadmin undeploy `$SERVER_DIR/bin/asadmin list-applications | grep frenchpress | cut -f 1 -d " "`
    elif [ "$SERVER" == "tomee" ] ; then
        rm $SERVER_DIR/webapps/frenchpress.war
    fi
}

function configureJdbc() {
    if [ "$SERVER" == "glassfish" ] ; then
        $SERVER_DIR/glassfish/bin/asadmin delete-jdbc-resource jdbc/frenchpress &> /dev/null
        $SERVER_DIR/glassfish/bin/asadmin delete-jdbc-connection-pool FrenchPressPool &> /dev/null

        if [ "$DB_VENDOR" == "pgsql" ] ; then
            RES_TYPE=javax.sql.XADataSource
            DS_CLASS_NAME=org.postgresql.xa.PGXADataSource
            PROPERTIES=user=$DB_USER:password=$DB_PASS:serverName=$DB_HOST:databaseName=$DB_NAME
        elif [ "$DB_VENDOR" == "mysql" ] ; then
            RES_TYPE=javax.sql.DataSource
            DS_CLASS_NAME=com.mysql.jdbc.jdbc2.optional.MysqlDataSource
            PROPERTIES=User=$DB_USER:Password=$DB_PASS:Host=$DB_HOST:DatabaseName=$DB_NAME
        fi

        echo Creating connection pool
        $SERVER_DIR/glassfish/bin/asadmin create-jdbc-connection-pool --restype=$RES_TYPE \
            --datasourceclassname=$DS_CLASS_NAME --property=$PROPERTIES FrenchPressPool > /dev/null

        echo Creating JDBC resource
        $SERVER_DIR/glassfish/bin/asadmin create-jdbc-resource --connectionpoolid=FrenchPressPool \
            jdbc/frenchpress > /dev/null

        echo Pinging connection pool
        $SERVER_DIR/glassfish/bin/asadmin ping-connection-pool FrenchPressPool   

        if [ $? -ne 0 ] ; then
            echo Ping the database server failed.  Please correct the error and try again.
            exit -1
        fi
    elif [ "$SERVER" == "tomee" ] ; then
        if [ "$DB_VENDOR" == "pgsql" ] ; then
            echo -e "<tomee><Resource id=\"frenchpress\" type=\"DataSource\">\nJdbcDriver   org.postgresql.Driver\nJdbcUrl  jdbc:postgresql://$DB_HOST/$DB_NAME\nUserName $DB_USER\nPassword $DB_PASS\n</Resource></tomee>" > $SERVER_DIR/conf/tomee.xml
        fi
    fi
}

function sqlShell() {
    if [ "$DB_VENDOR" == "pgsql" ] ; then
        psql -U $DB_USER -h $DB_HOST $DB_NAME
    elif [ "$DB_VENDOR" == "mysql" ] ; then
        mysql -u $DB_USER -h $DB_HOST -p $DB_NAME
    fi
}

function setVendor() {
    if [ "$1" != "pgsql" -a "$1" != "mysql" ] ; then
        echo "Unsupported DB vendor ($1). Support vendors are 'pgsql' and 'mysql'."
        exit -1
    fi

    DB_VENDOR=$1
    echo Database vendor set to $DB_VENDOR
}

function installServer() {
    if [ "$SERVER" == "glassfish" ] ; then
        installGlassFish
    elif [ "$SERVER" == "tomee" ] ; then
        installTomee
    fi
}

function installGlassFish() {
    # This function needs to take into account $SERVER_DIR during extraction
    if [ ! -e glassfish.zip ] ; then
        wget http://dlc.sun.com.edgesuite.net/glassfish/4.0/nightly/glassfish-4.0-web-b86-04_24_2013.zip -O glassfish.zip
            #http://download.java.net/glassfish/3.1.2/release/glassfish-3.1.2.zip
    fi

    echo Extracting GlassFish
    unzip glassfish.zip > /dev/null
    sed -i -e "s/9009\"/9009\" debug-enabled=\"true\"/" glassfish4/glassfish/domains/domain1/config/domain.xml
    copyJdbcJars $SERVER_DIR/glassfish/lib
}

function installTomee() {
    if [ ! -e tomee.tar.gz ] ; then
        wget http://www.globalish.com/am/tomee/tomee-1.5.2/apache-tomee-1.5.2-jaxrs.tar.gz -O tomee.tar.gz
    fi
    
    echo Extracting Tomee
    tar xf tomee.tar.gz
    mv apache-tomee-jaxrs* tomee
    copyJdbcJars $SERVER_DIR/lib
}

function copyJdbcJars() {
    for JAR in $JAR_DIR/*.jar ; do
        FILE=`basename $JAR`
        if [ ! -e $1/$FILE ] ; then
            cp $JAR $1
        fi
    done
}

function reinstallGlassFish() {
    stopServer &> /dev/null
    rm -rf $SERVER_DIR
    installGlassFish
}

function reinstallTomee() {
    stopServer &> /dev/null
    rm -rf $SERVER_DIR
    installTomee
}

function tailLog() {
    if [ "$SERVER" == "glassfish" ] ; then
        tail -f $SERVER_DIR/glassfish/domains/domain1/logs/server.log
    elif [ "$SERVER" == "tomee" ] ; then
        tail -f $SERVER_DIR/logs/catalina.out
    fi
}
    

function usage() {
    echo "The arguments to use are:"
    echo "  -c : Open SQL shell"
    echo "  -d : Install Frenchpress"
    echo "  -D : Drop the existing tables"
    echo "  -i : Install GlassFish, downloading if necessary"
    echo "  -I : Reinstall GlassFish"
    echo "  -j : Configure the JDBC resources on the server"
    echo "  -l : Load sample media data and exit"
    echo "  -r : Restart the server"
    echo "  -s : Start the server"
    echo "  -S : Stop the server"
    echo "  -t : Tail the server log"
    echo "  -u : Uninstall FrenchPress"
    echo "  -v : Set Database vendor"
}

while getopts cdgGijlrsStuv: opt
do
    case "$opt" in
        c) sqlShell ;;
        D) dropTables ;;
        d) deploy ;;
        i) installServer ;;
        I) reinstallServer ;;
        j) configureJdbc ;;
        l) loadSampleData ;;
        r) stopServer ; startServer ;;
        s) startServer ;;
        S) stopServer ;;
        t) tailLog ;;
        u) uninstall ;;
        v) setVendor $OPTARG ;;
        *) usage ; exit -1 ;;
    esac
done
