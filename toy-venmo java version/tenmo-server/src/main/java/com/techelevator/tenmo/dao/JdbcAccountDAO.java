package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAll(){
        List<Account> accounts = new ArrayList<>();
        String sqlQuery = "SELECT account_id, user_id, balance FROM accounts;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
        while(results.next()){
            Account theAccount = mapRowToAccount(results);
            accounts.add(theAccount);
        }
        return accounts;
    }

    @Override
    public List<AccountDTO> findAllAccountDTO(){
        List<AccountDTO> accountDTOs = new ArrayList<>();
        String sqlQuery = "SELECT account_id, accounts.user_id, users.username FROM accounts INNER JOIN users on users.user_id=accounts.user_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
        while(results.next()){
            AccountDTO theAccountDTO = mapRowToAccountDTO(results);
            accountDTOs.add(theAccountDTO);
        }
        return accountDTOs;
    }

    @Override
    public Account findAccountByUser_Id(Long user_id){
        String sqlQuery = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ? ;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, user_id);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }

            throw new EmptyResultDataAccessException(user_id.intValue());

        }


    @Override
    public Account findByAccount_Id(Long account_id){
        String sqlQuery = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ? ;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, account_id);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new EmptyResultDataAccessException(account_id.intValue());
    }

    @Override
    public Account updateAccountBalance(Account account, double amount){
        String sqlQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ? ;";
        jdbcTemplate.update(sqlQuery, amount, account.getAccount_id());
        Account updatedAccount = findByAccount_Id(account.getAccount_id());
        return updatedAccount;
    }


    @Override
    public Double returnAccountBalance(Account account) {
        String sqlQuery = "SELECT account_id, user_id, balance FROM accounts WHERE account_id = ? ;";
        Long account_id = account.getAccount_id();
        SqlRowSet rowset = jdbcTemplate.queryForRowSet(sqlQuery, account_id);
        if (rowset.next()) {
            return mapRowToAccount(rowset).getBalance();
        }
        throw new EmptyResultDataAccessException(account_id.intValue());
    }


    //JDBC utility functions

        private Account mapRowToAccount (SqlRowSet rowSet){
            Account theAccount = new Account();
            theAccount.setAccount_id(rowSet.getLong("account_id"));
            theAccount.setUser_id(rowSet.getLong("user_id"));
            theAccount.setBalance(rowSet.getDouble("balance"));
            return theAccount;
        }

        private AccountDTO mapRowToAccountDTO(SqlRowSet row){
        AccountDTO theAccountDTO = new AccountDTO();
        theAccountDTO.setAccount_id(row.getLong("account_id"));
        theAccountDTO.setUser_id(row.getLong("user_id"));
        theAccountDTO.setUsername(row.getString("username"));
        return theAccountDTO;
        }

    }



