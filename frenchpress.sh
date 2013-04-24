#!/bin/bash

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
    $SERVER_DIR/bin/asadmin start-domain
}

function stopServer() {
    $SERVER_DIR/bin/asadmin stop-domain
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

function install() {
    mvn -Dmaven.test.skip=true package && 
        $SERVER_DIR/bin/asadmin deploy --force target/frenchpress*/
}

function uninstall() {
    $SERVER_DIR/bin/asadmin undeploy `$SERVER_DIR/bin/asadmin list-applications | grep frenchpress | cut -f 1 -d " "`
}

function configureJdbc() {
    for JAR in $JAR_DIR/*.jar ; do
        FILE=`basename $JAR`
        if [ ! -e $SERVER_DIR/glassfish/lib/$FILE ] ; then
            cp $JAR $SERVER_DIR/glassfish/lib
            COPIED=true
        fi
    done
    if [ "$COPIED" == "true" ] ; then
        echo JDBC Drivers have been copied to the server.  New restarting the server to allow the changes to take effect
        $SERVER_DIR/bin/asadmin restart-domain
    fi

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

function installGlassFish() {
    # This function needs to take into account $SERVER_DIR during extraction
    if [ ! -e glassfish.zip ] ; then
        wget http://dlc.sun.com.edgesuite.net/glassfish/4.0/nightly/glassfish-4.0-web-b86-04_24_2013.zip -O glassfish.zip
            #http://download.java.net/glassfish/3.1.2/release/glassfish-3.1.2.zip
    fi

    echo Extracting GlassFish
    unzip glassfish.zip > /dev/null
}

function reinstallGlassFish() {
    stopServer &> /dev/null
    rm -rf $SERVER_DIR
    installGlassFish
}

function usage() {
    echo "The arguments to use are:"
    echo "  -c : Open SQL shell"
    echo "  -d : Drop the existing tables"
    echo "  -g : Install GlassFish, downloading if necessary"
    echo "  -G : Reinstall GlassFish"
    echo "  -i : Install Frenchpress"
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
        d) dropTables ;;
        g) installGlassFish ;;
        G) reinstallGlassFish ;;
        i) install ;;
        j) configureJdbc ;;
        l) loadSampleData ;;
        r) stopServer ; startServer ;;
        s) startServer ;;
        S) stopServer ;;
        t) tail -f $SERVER_DIR/glassfish/domains/domain1/logs/server.log ;;
        u) uninstall ;;
        v) setVendor $OPTARG ;;
        *) usage ; exit -1 ;;
    esac
done
