package edu.cmis.zfit.service;

public class ServiceException extends Exception {
    public ServiceException(Throwable err) {
        super(err);
    }

    public ServiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}