package com.hanpyeon.academyapi.account.service.update;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.service.Account;

interface AccountUpdateCommandHandler {
    void update(final Account targetAccount, final AccountUpdateCommand updateCommand);
}
