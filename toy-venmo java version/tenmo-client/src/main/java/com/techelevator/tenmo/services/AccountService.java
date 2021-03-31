package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.*;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AccountService {

    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();


    public static String AUTH_TOKEN = "";
    public AccountService(String url) {
        this.baseUrl = url;
    }
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public Account getAccount(String token){
        Account theAccount = null;
            theAccount = restTemplate.exchange(baseUrl + "accounts", HttpMethod.GET, makeAuthEntity(token), Account.class).getBody();
        return theAccount;
    }

    public double getBalance(String token){
        Account theAccount = getAccount(token);
        return theAccount.getBalance();
    }


    public TransferDTO sendFunds(TransferDTO transferDTO, String token){
        AUTH_TOKEN = token;
        TransferDTO theTransferDTO = new TransferDTO();

        try {
            theTransferDTO = restTemplate.postForObject(baseUrl + "/transfers/send", makeTransferDTOEntity(transferDTO), TransferDTO.class);
        } catch (RestClientResponseException ex) {
            console.printError("Invalid entry. Please try again.");
            theTransferDTO = null;
        } catch (ResourceAccessException ex){
            console.printError(ex.getMessage());
            theTransferDTO = null;
        }


        return theTransferDTO;
    }


    public Transfer requestFunds(Transfer transfer, String token){
        AUTH_TOKEN = token;
        Transfer theTransfer = new Transfer();
        try {
            theTransfer = restTemplate.postForObject(baseUrl + "/transfers/request", makeTransferEntity(transfer), Transfer.class);
        }catch (RestClientResponseException ex) {
            console.printError("Invalid entry. Please try again.");
            theTransfer = null;
        } catch (ResourceAccessException ex){
            console.printError(ex.getMessage());
            theTransfer = null;
        }
        return theTransfer;
    }

    public TransferPO[] listFinalizedTransfers(String token){
        TransferPO[] transfers = restTemplate.exchange(baseUrl + "/transfers/finalized", HttpMethod.GET, makeAuthEntity(token), TransferPO[].class).getBody();
        return transfers;
    }

    public TransferPO[] listPendingTransfersThatRequireUserApproval(String token){
        TransferPO[] transfers = restTemplate.exchange(baseUrl + "/transfers/pending/from", HttpMethod.GET, makeAuthEntity(token), TransferPO[].class).getBody();
        return transfers;
    }

    public AccountDTO[] getAllAccountDTOs(String token){
        AccountDTO[] allAccountDTOs = restTemplate.exchange(baseUrl+"/accounts/accountDTOlist", HttpMethod.GET, makeAuthEntity(token), AccountDTO[].class).getBody();
        return allAccountDTOs;
    }

    public TransferPO getTransferPOByTransfer_Id(long transfer_id, String token){
        TransferPO transferPO = new TransferPO();
        try {
            transferPO = restTemplate.exchange(baseUrl + "/transfers/" + transfer_id, HttpMethod.GET, makeAuthEntity(token), TransferPO.class).getBody();
        }catch (RestClientResponseException ex) {
            console.printError("Invalid entry. Please try again.");
            transferPO = null;
        } catch (ResourceAccessException ex){
            console.printError(ex.getMessage());
            transferPO = null;
        }
        return transferPO;
    }


    public boolean approvePendingFromTransfer(long transfer_id, String token){
        boolean transferApproved = false;
        try {
            transferApproved = restTemplate.exchange(baseUrl + "/transfers/approve/" + transfer_id, HttpMethod.POST, makeAuthEntity(token), boolean.class).getBody();
             } catch (RestClientResponseException ex) {
        console.printError("Invalid entry. Please try again.");
    } catch (ResourceAccessException ex){
        console.printError(ex.getMessage());
    }
            return transferApproved;
    }

    public void rejectPendingFromTransfer(long transfer_id, String token){
//        restTemplate.exchange(baseUrl+"/transfers/reject/" + transfer_id, HttpMethod.POST, makeAuthEntity(token), void.class);
        try {
            restTemplate.put(baseUrl + "/transfers/reject/" + transfer_id, makeAuthEntity(token));
            System.out.println();
            System.out.println("Transfer #" +transfer_id + " has been rejected.");
        } catch (RestClientResponseException ex) {
            console.printError("Invalid entry. Please try again.");
        } catch (ResourceAccessException ex){
            console.printError(ex.getMessage());
        }
    }

    private TransferDTO makeTransferDTO(Long from_AccountId, Long to_AccountId, Double amount){
        TransferDTO theTransferDTO = new TransferDTO();
        theTransferDTO.setFrom_id(from_AccountId);
        theTransferDTO.setTo_id(to_AccountId);
        theTransferDTO.setAmount(amount);
        return theTransferDTO;
    }

    private HttpEntity<TransferDTO> makeTransferDTOEntity(TransferDTO transferDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, headers);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private HttpEntity makeAuthEntity(String AUTH_TOKEN) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
