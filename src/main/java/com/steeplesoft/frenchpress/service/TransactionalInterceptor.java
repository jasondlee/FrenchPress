/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.service;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import javax.transaction.Transactional;

/**
 *
 * @author jdlee
 */
//@Transactional
//@Interceptor
public class TransactionalInterceptor implements Serializable {
    @Resource
    protected UserTransaction utx;

    @AroundInvoke
    public Object manageTransaction(InvocationContext context) throws Throwable {
        boolean started = false;
        if (utx.getStatus() != Status.STATUS_ACTIVE) {
            utx.begin();
            started = true;
        }
        Object result = null;

        try {
            result = context.proceed();

            if (started) {
                utx.commit();
            }
        } catch (Throwable t) {
            if (started) {
                utx.rollback();
            }

            throw t;
        }

        return result;
    }
}
