package com.steeplesoft.frenchpress.view;

import com.steeplesoft.frenchpress.service.impl.DataAccessController;
import com.steeplesoft.frenchpress.view.util.JsfUtil;
import com.steeplesoft.frenchpress.view.util.Paginator;
import java.io.Serializable;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

public abstract class ControllerBean implements Serializable {
    public static String NAV_REDIRECT = "?faces-redirect=true";

    private String NAV_ADD  = "/admin/attachments/form";
    private String NAV_EDIT = "/admin/attachments/form";
    private String NAV_LIST = "/admin/attachments/list";
    private String NAV_VIEW = "/admin/attachments/view";

    @ManagedProperty("#{dataAccessController}")
    private DataAccessController dataAccess;
    private DataModel dataModel;
    private int rowsPerPage = 5;
    private int selectedItemIndex = -1;
    private Object current;
    private Paginator paginator;

    //**************************************************************************
    // Abstract methods

    public abstract Class getEntityClass();

    public DataAccessController getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(DataAccessController dataAccess) {
        this.dataAccess = dataAccess;
    }

    protected void setNavigationIds(String add, String edit, String list, String view) {
        this.NAV_ADD = add;
        this.NAV_EDIT = edit;
        this.NAV_LIST = list;
        this.NAV_VIEW = view;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        resetList();
    }

    public void resetList() {
        dataModel = null;
    }

    public void resetPagination(ValueChangeEvent vce) {
        paginator = null;
    }

    public Paginator getPaginator() {
        if (paginator == null) {
            paginator = new Paginator(rowsPerPage) {

                @Override
                public int getItemsCount() {
                    return dataAccess.count(getEntityClass());
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(dataAccess.findAll(getEntityClass()));
                            //.findRange(getEntityClass(), getPageFirstItem(), getPageFirstItem()+getPageSize()));
                }
            };
        }

        return paginator;

    }

    public void next() {
        getPaginator().nextPage();
        resetList();
    }

    public void previous() {
        getPaginator().previousPage();
        resetList();
    }



    //**************************************************************************
    // prepareFoo and Foo action methods
    public String prepareList() {
        resetList();
        return NAV_LIST + NAV_REDIRECT;
    }

    public DataModel getList() {
        if (dataModel == null) {
            dataModel = getPaginator().createPageDataModel();
        }

        return dataModel;
    }

    public String prepareCreate() {
        setSelected(newEntityInstance());
        selectedItemIndex = -1;
        return NAV_ADD;// + NAV_REDIRECT;
    }

    public String create() {
        try {
            dataAccess.create(getSelected());
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupMemberCreated")); // Left as an example :)
            JsfUtil.addSuccessMessage("Group member created");
            return NAV_LIST + NAV_REDIRECT;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, "A persistence error occurred.");
            return null;
        }
    }

    public String prepareEdit() {
        setSelected(getList().getRowData());
        selectedItemIndex = paginator.getPageFirstItem() + getList().getRowIndex();
        return NAV_EDIT;// + NAV_REDIRECT;
    }

    public String edit() {
        try {
            dataAccess.edit(getSelected());
            JsfUtil.addSuccessMessage("Group member updated");
            return NAV_EDIT + NAV_REDIRECT;
        } catch (Exception e) {
           JsfUtil.addErrorMessage(e, "A persistence error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    public String delete() {
        setSelected(getList().getRowData());
        selectedItemIndex = paginator.getPageFirstItem() + getList().getRowIndex();
        try {
            dataAccess.remove(getSelected());
            JsfUtil.addSuccessMessage("Group member deleted");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "A persistence error occurred.");
        }
        resetList();
        return NAV_LIST + NAV_REDIRECT;
    }

    public String prepareView() {
        setSelected(getList().getRowData());
        selectedItemIndex = paginator.getPageFirstItem() + getList().getRowIndex();
        return NAV_VIEW + NAV_REDIRECT;
    }

    public String checkViewData() {
        // If an ID is in the URL, that overrides what might be in the session state
        String id = JsfUtil.getRequestParameter("id");
        if (id != null) {
            setSelected(dataAccess.find(getEntityClass(), Long.valueOf(id)));
        }

        if (current != null) {
            return NAV_VIEW + NAV_REDIRECT;
        } else {
            return NAV_LIST + NAV_REDIRECT;
        }
    }

    public Object getSelected() {
        if (current == null) {
            setSelected(newEntityInstance());
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Object selected) {
        this.current = selected;
    }

    protected Object newEntityInstance() {
        Object obj = null;
        try {
            obj = getEntityClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}