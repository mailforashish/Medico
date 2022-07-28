package com.medico.app.response;

public class CancellationList {
    private String cancel_reason;

    public CancellationList(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }
}
