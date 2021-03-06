package com.intelligentz.sahasara.model.Data;

import com.intelligentz.sahasara.exception.IdeabizException;
import com.intelligentz.sahasara.model.ideabiz.ApplicationInformation;

/**
 * Created by Malinda on 10/19/2015.
 *
 * Impliment this interface depend on database connection
 */
public interface DataInterface {
    public ApplicationInformation getAppInfo(String applicationId);
    public ApplicationInformation loadPropFile() throws IdeabizException;
    boolean saveTokenFile(ApplicationInformation applicationInformation);
}
