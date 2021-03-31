package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.AccountNotFoundException;
import com.techelevator.tenmo.exceptions.TransferNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.TransferPO;

import java.security.Principal;
import java.util.List;

public interface TransferDAO {

    List<Transfer> findAll();

    Transfer findTransferByTransfer_Id (long transfer_id)throws TransferNotFoundException;

    List<TransferPO> findTransfersByUser_Id(long user_id);

    List<TransferPO> findNonPendingTransfersByUser_Id(long user_id);

    List<TransferPO> findPendingTransferRequestsByUser_Id(long user_id);

    TransferPO findTransferPOByTransfer_Id(long transfer_id, Principal principal)throws TransferNotFoundException;

    List<TransferPO> findPendingFromTransfersByUser_Id(long user_id);

    List<TransferPO> findPendingToTransfersByUser_Id(long user_id);

    TransferDTO sendFunds (Long fromUserId, Long toUserId, double amount)throws AccountNotFoundException;

    Transfer requestFunds(Long fromAccountId, Long toAccountId, double amount)throws AccountNotFoundException;

    boolean approvePendingTransfer(long transfer_id, Principal principal)throws TransferNotFoundException;

    void rejectPendingTransfer(long transfer_id, Principal principal)throws TransferNotFoundException;



}
