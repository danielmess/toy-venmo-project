package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.exceptions.AccountNotFoundException;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;
import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {
    private UserDAO userDAO;
    private AccountDAO accountDAO;
    private TransferDAO transferDAO;



    public AccountController(UserDAO userDAO, AccountDAO accountDAO, TransferDAO transferDAO) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public Account findAccountForLoggedInUser(Principal principal){
        if (principal!= null){
            Long user_id = getCurrentUserID(principal);
            return accountDAO.findAccountByUser_Id(user_id);
        } else {
            System.out.println("Can't print the account because they aren't logged in.");
            return null;
        }
    }

    @RequestMapping(path="/accounts/balance", method = RequestMethod.GET)
    public Double returnAccountBalance(Principal principal) {
        Account theAccount = findAccountForLoggedInUser(principal);
        return accountDAO.returnAccountBalance(theAccount);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path="/transfers/send", method = RequestMethod.POST)
    public TransferDTO sendFunds(@Valid @RequestBody TransferDTO transferInfo, Principal principal) throws AccountNotFoundException {
        if (transferInfo.getFrom_id() == getCurrentUserAccountID(principal) && transferInfo.getTo_id()!= getCurrentUserAccountID(principal)) {
            return transferDAO.sendFunds(transferInfo.getFrom_id(), transferInfo.getTo_id(), transferInfo.getAmount());
        } else {
            throw new AccessDeniedException("Incorrect info entered, please try again.");
        }
    }

    @RequestMapping(path="/transfers/request", method = RequestMethod.POST)
    public Transfer requestFunds(@Valid @RequestBody Transfer transferInfo, Principal principal) throws AccountNotFoundException {
        if (transferInfo.getAccount_to() == getCurrentUserAccountID(principal) &&transferInfo.getAccount_from()!= getCurrentUserAccountID(principal)) {
            return transferDAO.requestFunds(transferInfo.getAccount_from(), transferInfo.getAccount_to(), transferInfo.getAmount());
        } else {
            throw new AccessDeniedException("\n Incorrect info entered, please try again.");
        }
    }

    @RequestMapping(path="/transfers/{id}", method = RequestMethod.GET)
    public TransferPO findTransferPOByTransfer_Id(@PathVariable("id") long transfer_id, Principal principal) throws TransferNotFoundException{
        return transferDAO.findTransferPOByTransfer_Id(transfer_id, principal);
    }

    @RequestMapping(path = "/accounts/accountDTOlist", method = RequestMethod.GET)
    public List<AccountDTO> findAllAccountDTOs() throws AccountNotFoundException{
         return accountDAO.findAllAccountDTO();
    }

    @RequestMapping(path = "/transfers/user", method = RequestMethod.GET)
        public List<TransferPO> findAllTransfersForPrincipal(Principal principal){
        return transferDAO.findTransfersByUser_Id(getCurrentUserID(principal));
    }

    @RequestMapping(path = "/transfers/pending/from", method = RequestMethod.GET)
    public List<TransferPO> findPendingFromTransfersForPrincipal(Principal principal){
        return transferDAO.findPendingFromTransfersByUser_Id(getCurrentUserID(principal));
    }

    @RequestMapping(path = "/transfers/pending/to", method = RequestMethod.GET)
    public List<TransferPO> findPendingToTransfersForPrincipal(Principal principal){
        return transferDAO.findPendingFromTransfersByUser_Id(getCurrentUserID(principal));
    }

    @RequestMapping(path = "/transfers/finalized", method = RequestMethod.GET)
    public List<TransferPO> findNonPendingTransfersForPrincipal(Principal principal){
        return transferDAO.findNonPendingTransfersByUser_Id(getCurrentUserID(principal));
    }
    
    @RequestMapping(path = "/transfers/approve/{id}", method = RequestMethod.POST)
    public boolean approvePendingTransfer(@PathVariable("id") long transfer_id, Principal principal) throws TransferNotFoundException{
       return transferDAO.approvePendingTransfer(transfer_id, principal);
    }

    @RequestMapping(path = "/transfers/reject/{id}", method = RequestMethod.PUT)
    public void rejectPendingTransfer(@PathVariable("id") long transfer_id, Principal principal) throws TransferNotFoundException{
       transferDAO.rejectPendingTransfer(transfer_id, principal);
    }

    private long getCurrentUserID(Principal principal){
        User theUser = userDAO.findByUsername(principal.getName());
        return theUser.getId();
    }

    private long getCurrentUserAccountID(Principal principal){
       return accountDAO.findAccountByUser_Id(getCurrentUserID(principal)).getAccount_id();
    }

}

