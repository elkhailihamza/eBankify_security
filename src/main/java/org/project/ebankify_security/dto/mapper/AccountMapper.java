package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.ebankify_security.dto.AccountDTO;
import org.project.ebankify_security.dto.response.AccountResDto;
import org.project.ebankify_security.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResDto getAccountToAccountViewDto(Account account);

    @Mapping(source = "owner.id", target = "owner_id")
    AccountDTO toAccountDTO(Account account);
    Account toAccount(AccountDTO accountDTO);
}
