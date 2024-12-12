package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.AccountDTO;
import org.project.ebankify_security.dto.response.AccountResDto;
import org.project.ebankify_security.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResDto getAccountToAccountViewDto(Account account);
    AccountDTO toAccountDTO(Account account);
    Account toAccount(AccountDTO accountDTO);
}
