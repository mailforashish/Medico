package com.medico.app.interfaceClass;

import com.medico.app.response.Address.AddressResult;

public interface AddressAction {
    public void addressChanges(String action, AddressResult addressResult, int pos);
}
