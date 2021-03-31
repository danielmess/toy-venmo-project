package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;

import java.util.List;

public interface AccountDAO {

    List<Account> findAll();

    List<AccountDTO> findAllAccountDTO();

    Account findAccountByUser_Id(Long user_id);

    Account findByAccount_Id(Long account_id);

    Account updateAccountBalance(Account account, double amount);

    Double returnAccountBalance(Account account);




}
