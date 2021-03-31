package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.AccountNotFoundException;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;
import com.techelevator.tenmo.model.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;

@Component
public class JdbcTransferDAO implements TransferDAO{
    private JdbcTemplate jdbcTemplate;

    private UserDAO userDAO;
    private AccountDAO accountDAO;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate, AccountDAO accountDAO, UserDAO userDAO) {

        this.jdbcTemplate = jdbcTemplate;
        this.accountDAO =accountDAO;
        this.userDAO = userDAO;
    }


    @Override
    public List<Transfer> findAll(){
        List<Transfer> transfers = new ArrayList<>();
        String sqlQuery = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
                    while(results.next()){
                    Transfer theTransfer = mapRowToTransfer(results);
                    transfers.add(theTransfer);
                }
                return transfers;
    }

    @Override
    public Transfer findTransferByTransfer_Id(long transfer_id) throws TransferNotFoundException {
        String sqlQuery = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, " +
                "amount FROM transfers WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, transfer_id);
        if (rowSet.next()){
            return mapRowToTransfer(rowSet);
        }
        throw new EmptyResultDataAccessException((int) transfer_id);
    }

    @Override
    public List<TransferPO> findTransfersByUser_Id(long user_id){
        List<TransferPO> transferPOs = new ArrayList<>();
        String sqlQuery =
            "SELECT DISTINCT transfers.transfer_id, amount, transfer_statuses.transfer_status_desc, transfer_types.transfer_type_desc,"
            + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_from)) AS from_username,"
   + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_to)) AS to_username"
   + " FROM transfers "
   + " INNER JOIN accounts ON accounts.account_id = transfers.account_to OR accounts.account_id = transfers.account_from"
   + " INNER JOIN users ON accounts.user_id = users.user_id"
   + " INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id"
   + " INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id"
   + " WHERE users.user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, user_id);
        while(results.next()){
            TransferPO theTransferPO = mapRowToTransferPO(results);
            transferPOs.add(theTransferPO);
        }
        return transferPOs;
    }

    @Override
    public List<TransferPO> findNonPendingTransfersByUser_Id(long user_id){
        List<TransferPO> transferPOs = new ArrayList<>();
        String sqlQuery =
                "SELECT DISTINCT transfers.transfer_id, amount, transfer_statuses.transfer_status_desc, transfer_types.transfer_type_desc,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_from)) AS from_username,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_to)) AS to_username"
                        + " FROM transfers "
                        + " INNER JOIN accounts ON accounts.account_id = transfers.account_to OR accounts.account_id = transfers.account_from"
                        + " INNER JOIN users ON accounts.user_id = users.user_id"
                        + " INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id"
                        + " INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id"
                        + " WHERE users.user_id = ? AND transfers.transfer_status_id IN (2,3);";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, user_id);
        while(results.next()){
            TransferPO theTransferPO = mapRowToTransferPO(results);
            transferPOs.add(theTransferPO);
        }
        return transferPOs;
    }

    @Override
    public List<TransferPO> findPendingTransferRequestsByUser_Id(long user_id){
        List<TransferPO> transferPOs = new ArrayList<>();
        String sqlQuery =
                "SELECT DISTINCT transfers.transfer_id, amount, transfer_statuses.transfer_status_desc, transfer_types.transfer_type_desc,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_from)) AS from_username,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_to)) AS to_username"
                        + " FROM transfers "
                        + " INNER JOIN accounts ON accounts.account_id = transfers.account_to OR accounts.account_id = transfers.account_from"
                        + " INNER JOIN users ON accounts.user_id = users.user_id"
                        + " INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id"
                        + " INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id"
                        + " WHERE users.user_id = ? AND transfers.transfer_status_id = 1;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, user_id);
        while(results.next()){
            TransferPO theTransferPO = mapRowToTransferPO(results);
            transferPOs.add(theTransferPO);
        }
        return transferPOs;
    }

    @Override
    public TransferPO findTransferPOByTransfer_Id(long transfer_id, Principal principal)throws TransferNotFoundException{
        String sqlQuery =
                "SELECT DISTINCT transfers.transfer_id, amount, transfer_statuses.transfer_status_desc, transfer_types.transfer_type_desc,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_from)) AS from_username,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_to)) AS to_username"
                        + " FROM transfers "
                        + " INNER JOIN accounts ON accounts.account_id = transfers.account_to OR accounts.account_id = transfers.account_from"
                        + " INNER JOIN users ON accounts.user_id = users.user_id"
                        + " INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id"
                        + " INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id"
                        + " WHERE transfer_id = ?";
        Transfer theTransfer = findTransferByTransfer_Id(transfer_id);
        Account to_account = accountDAO.findByAccount_Id(theTransfer.getAccount_to());
        Account from_account = accountDAO.findByAccount_Id(theTransfer.getAccount_from());
        //If statement checks to make sure user is either the requestee or requester for the transfer.
        if(from_account.getUser_id() == userDAO.findByUsername(principal.getName()).getId()  || to_account.getUser_id() == userDAO.findByUsername(principal.getName()).getId()) {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, transfer_id);
            if (rowSet.next()) {
                return mapRowToTransferPO(rowSet);
            }
            throw new EmptyResultDataAccessException((int) transfer_id);
        } else throw new AccessDeniedException("Logged in user is not the appropriate accountholder");
    }

    @Override
    public List<TransferPO> findPendingFromTransfersByUser_Id(long user_id){
        List<TransferPO> transferPOs = new ArrayList<>();
        Account fromAccount = accountDAO.findAccountByUser_Id(user_id);
        Long fromAccountID = fromAccount.getAccount_id();
        String sqlQuery =
                "SELECT DISTINCT transfers.transfer_id, amount, transfer_statuses.transfer_status_desc, transfer_types.transfer_type_desc,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_from)) AS from_username,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_to)) AS to_username"
                        + " FROM transfers "
                        + " INNER JOIN accounts ON accounts.account_id = transfers.account_to OR accounts.account_id = transfers.account_from"
                        + " INNER JOIN users ON accounts.user_id = users.user_id"
                        + " INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id"
                        + " INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id"
                        + " WHERE account_from = ? AND transfers.transfer_status_id = 1;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, fromAccountID);
        while(results.next()){
            TransferPO theTransferPO = mapRowToTransferPO(results);
            transferPOs.add(theTransferPO);
        }
        return transferPOs;
    }

    @Override
    public List<TransferPO> findPendingToTransfersByUser_Id(long user_id){
        List<TransferPO> transferPOs = new ArrayList<>();
        Account toAccount = accountDAO.findAccountByUser_Id(user_id);
        Long toAccountID = toAccount.getAccount_id();
        String sqlQuery =
                "SELECT DISTINCT transfers.transfer_id, amount, transfer_statuses.transfer_status_desc, transfer_types.transfer_type_desc,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_from)) AS from_username,"
                        + " (SELECT username FROM users WHERE users.user_id = (SELECT user_id FROM accounts  WHERE users.user_id = accounts.user_id AND accounts.account_id = transfers.account_to)) AS to_username"
                        + " FROM transfers "
                        + " INNER JOIN accounts ON accounts.account_id = transfers.account_to OR accounts.account_id = transfers.account_from"
                        + " INNER JOIN users ON accounts.user_id = users.user_id"
                        + " INNER JOIN transfer_types ON transfers.transfer_type_id = transfer_types.transfer_type_id"
                        + " INNER JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id"
                        + " WHERE account_to = ? AND transfers.transfer_status_id = 1;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, toAccountID);
        while(results.next()){
            TransferPO theTransferPO = mapRowToTransferPO(results);
            transferPOs.add(theTransferPO);
        }
        return transferPOs;
    }

    @Override
    public TransferDTO sendFunds (Long fromAccountId, Long toAccountId, double amount)throws AccountNotFoundException{
        TransferDTO theTransferDTO = new TransferDTO();
        Account account_from = accountDAO.findByAccount_Id(fromAccountId);
        Account account_to = accountDAO.findByAccount_Id(toAccountId);
        if (account_from.getBalance() >= amount) {
            String sqlQuery = "BEGIN TRANSACTION; " +
                    "UPDATE accounts SET balance = balance + ? WHERE account_id = ?; " +
                    "UPDATE accounts SET balance = balance - ? WHERE account_id = ?; " +
                    "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES(2, 2, ?, ?, ?);" +
                    "COMMIT; ";

            jdbcTemplate.update(sqlQuery,
                    amount,
                    account_to.getAccount_id(),
                    amount,
                    account_from.getAccount_id(),
                    account_from.getAccount_id(),
                    account_to.getAccount_id(),
                    amount);

            theTransferDTO.setFrom_id(fromAccountId);
            theTransferDTO.setTo_id(toAccountId);
            theTransferDTO.setAmount(amount);
            return theTransferDTO;
        }
        return theTransferDTO;
    }

    @Override
    public Transfer requestFunds(Long fromAccountId, Long toAccountId, double amount) throws AccountNotFoundException{
        Account account_from = accountDAO.findByAccount_Id(fromAccountId);
        Account account_to = accountDAO.findByAccount_Id(toAccountId);

        //Design decision made to not include a balance check against the account that funds are being requested from.
        //Decision was made so that system could not be abused to find out how much money is in another client's account.

        String sqlQuery = "BEGIN TRANSACTION; " +
                "INSERT INTO transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(1, 1, ?, ?, ?);" +
                "COMMIT; ";

        jdbcTemplate.update(sqlQuery,
                account_from.getAccount_id(),
                account_to.getAccount_id(),
                amount);

        Transfer theTransfer = new Transfer();
        theTransfer.setAccount_from(account_from.getAccount_id());
        theTransfer.setAccount_to(account_to.getAccount_id());
        theTransfer.setAmount(amount);
        theTransfer.setTransfer_type_id(1);
        theTransfer.setTransfer_status_id(1);

        return theTransfer;
    }

    @Override
    public boolean approvePendingTransfer(long transfer_id, Principal principal)throws TransferNotFoundException{
        boolean approved = false;
        Transfer theTransfer = findTransferByTransfer_Id(transfer_id);
        Account from_account = accountDAO.findByAccount_Id(theTransfer.getAccount_from());

        //Check to make sure that From account has enough funds
        //Check to make sure that the Transfer is pending
        //Check to make sure that the user is the requestee to prevent them from approving their own requests
        if (from_account.getBalance() >= theTransfer.getAmount() && theTransfer.getTransfer_status_id()==1 &&(from_account.getUser_id() == userDAO.findByUsername(principal.getName()).getId())) {
            String sql = "BEGIN TRANSACTION; " +
                    "UPDATE accounts SET balance = balance + ? WHERE account_id = ?; " +
                    "UPDATE accounts SET balance = balance - ? WHERE account_id = ?; " +
                    "UPDATE transfers SET transfer_status_id = 2 WHERE transfer_id = ?; " +
                    "COMMIT; ";

            jdbcTemplate.update(sql, theTransfer.getAmount(), theTransfer.getAccount_to(), theTransfer.getAmount(), theTransfer.getAccount_from(), transfer_id);
            approved = true;
        }
        return approved;
    }

    @Override
    public void rejectPendingTransfer(long transfer_id, Principal principal)throws TransferNotFoundException {
        Transfer theTransfer = findTransferByTransfer_Id(transfer_id);
        Account to_account = accountDAO.findByAccount_Id(theTransfer.getAccount_to());
        Account from_account = accountDAO.findByAccount_Id(theTransfer.getAccount_from());
        //If statement checks to make sure user is either the requestee or requester for the transfer.
        if(from_account.getUser_id() == userDAO.findByUsername(principal.getName()).getId()  || to_account.getUser_id() == userDAO.findByUsername(principal.getName()).getId()) {
            String sql = "BEGIN TRANSACTION; " +
                    "UPDATE transfers SET transfer_status_id = 3 WHERE transfer_id = ? ;" +
                    "COMMIT; ";
            jdbcTemplate.update(sql, transfer_id);
        } else throw new AccessDeniedException("Logged in user is not the appropriate accountholder");
    }


    private Transfer mapRowToTransfer(SqlRowSet rowSet){
    Transfer theTransfer = new Transfer();
    theTransfer.setTransfer_id(rowSet.getLong("transfer_id"));
    theTransfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
    theTransfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
    theTransfer.setAccount_from(rowSet.getLong("account_from"));
    theTransfer.setAccount_to(rowSet.getLong("account_to"));
    theTransfer.setAmount(rowSet.getDouble("amount"));
    return theTransfer;
    }

    private TransferPO mapRowToTransferPO(SqlRowSet rowSet){
        TransferPO theTransferPO = new TransferPO();
        theTransferPO.setTransfer_id(rowSet.getLong("transfer_id"));
        theTransferPO.setFrom_username(rowSet.getString("from_username"));
        theTransferPO.setTo_username(rowSet.getString("to_username"));
        theTransferPO.setTransfer_status_desc(rowSet.getString("transfer_status_desc"));
        theTransferPO.setTransfer_type_desc(rowSet.getString("transfer_type_desc"));
        theTransferPO.setAmount(rowSet.getDouble("amount"));
        return theTransferPO;
    }

}
