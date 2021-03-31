package com.techelevator.tenmo;

import com.techelevator.tenmo.models.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

import java.security.Principal;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String TRANSFER_MENU_OPTION_APPROVE = "Approve a transfer";
	private static final String TRANSFER_MENU_OPTION_REJECT = "Reject a transfer";
	private static final String TRANSFER_MENU_OPTION_GO_BACK = "Return to main menu";
	private static final String[] TRANSFER_MENU_OPTIONS = { TRANSFER_MENU_OPTION_APPROVE, TRANSFER_MENU_OPTION_REJECT, TRANSFER_MENU_OPTION_GO_BACK };
	private static final String TRANSFER_MENU_SELECT_TRANSFER_APPROVE = "Input the transfer ID# to approve";
	private static final String TRANSFER_MENU_SELECT_TRANSFER_REJECT = "Input the transfer ID# to reject";
	private static final String TRANSFER_SUBMENU_OPTION_GO_BACK = "Return to pending transfer menu";
	private static final String[] TRANSFER_MENU_SELECT_APPROVE_OPTIONS = { TRANSFER_MENU_SELECT_TRANSFER_APPROVE, TRANSFER_SUBMENU_OPTION_GO_BACK };
	private static final String[] TRANSFER_MENU_SELECT_REJECT_OPTIONS = { TRANSFER_MENU_SELECT_TRANSFER_REJECT, TRANSFER_SUBMENU_OPTION_GO_BACK };


    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountService accountService;
	public static String AUTH_TOKEN = "";

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
				printTransfer(currentUser.getToken());
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
				pendingTransferMenu();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendFunds();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestFunds();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				System.out.println("--------------------------------------------------");
				System.out.println("Thank you for using TEnmo! Please enjoy this Java.");
				System.out.println("--------------------------------------------------\n");
				System.out.println("_________________¶¶¶1___¶¶¶____¶¶¶1_______________");
				System.out.println("__________________¶¶¶____¶¶¶____1¶¶1______________");
				System.out.println("___________________¶¶¶____¶¶¶____¶¶¶______________");
				System.out.println("___________________¶¶¶____¶¶¶____¶¶¶______________");
				System.out.println("__________________¶¶¶____1¶¶1___1¶¶1______________");
				System.out.println("________________1¶¶¶____¶¶¶____¶¶¶1_______________");
				System.out.println("______________1¶¶¶____¶¶¶1___¶¶¶1_________________");
				System.out.println("_____________¶¶¶1___1¶¶1___1¶¶1___________________");
				System.out.println("____________1¶¶1___1¶¶1___1¶¶1____________________");
				System.out.println("____________1¶¶1___1¶¶1___1¶¶¶____________________");
				System.out.println("_____________¶¶¶____¶¶¶1___¶¶¶1___________________");
				System.out.println("______________¶¶¶¶___1¶¶¶___1¶¶¶__________________");
				System.out.println("_______________1¶¶¶1___¶¶¶1___¶¶¶¶________________");
				System.out.println("_________________1¶¶1____¶¶¶____¶¶¶_______________");
				System.out.println("___________________¶¶1____¶¶1____¶¶1______________");
				System.out.println("___________________¶¶¶____¶¶¶____¶¶¶______________");
				System.out.println("__________________1¶¶1___1¶¶1____¶¶1______________");
				System.out.println("_________________¶¶¶____¶¶¶1___1¶¶1_______________");
				System.out.println("________________11_____111_____11_________________");
				System.out.println("__________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("1¶¶¶¶¶¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("1¶¶¶¶¶¶¶¶¶¶¶__1¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("1¶¶_______¶¶__1¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("1¶¶_______¶¶__1¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("1¶¶_______¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("1¶¶_______¶¶__1¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("_¶¶¶¶¶¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("_¶¶¶¶¶¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________");
				System.out.println("__________¶¶___1¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1________");
				System.out.println("__________1¶¶___¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_________");
				System.out.println("____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶11__________");
				System.out.println("11______________________________________________11");
				System.out.println("1¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1");
				System.out.println("__11111111111¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶1111111111__");
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
    	String token = currentUser.getToken();
		System.out.println("--------------------------------------------------");
		System.out.printf ("Your current balance is $%.2f\n", accountService.getBalance(token));
		System.out.println("--------------------------------------------------\n");
	}

	private void viewTransferHistory() {
		String token = currentUser.getToken();
		TransferPO[] completedTransferPOs = accountService.listFinalizedTransfers(token);
		printFinalizedTransferPOarray(completedTransferPOs);
	}

	private void viewPendingRequests() {
		String token = currentUser.getToken();
		TransferPO[] transfers = accountService.listPendingTransfersThatRequireUserApproval(token);
		System.out.println("");
		printTransferRequestArray(transfers);
	}

	private void sendFunds() {
		String token = currentUser.getToken();
		TransferDTO theTransfer = new TransferDTO();
		theTransfer.setFrom_id(accountService.getAccount(token).getAccount_id());
		AccountDTO[] allAccountDTOs = accountService.getAllAccountDTOs(token);
		printAccountDTOarray(allAccountDTOs);
		long toUserId = (long)console.getUserInputInteger("Enter the user ID# to send funds to ");
		for(AccountDTO accountDTO: allAccountDTOs){
			if (accountDTO.getUser_id() == toUserId){
				theTransfer.setTo_id(accountDTO.getAccount_id());
				break;
			}
		}
		theTransfer.setAmount(console.getUserInputDouble("How much money would you like to send? "));
		TransferDTO attemptedTransfer = accountService.sendFunds(theTransfer, token);
		System.out.println();

		if(theTransfer.getAmount() > accountService.getBalance(token)) {
			System.out.println("--------------------------------------------------");
			System.out.println("Insufficient funds to complete this transfer.");
		} else if(attemptedTransfer!= null) {
			System.out.println("--------------------------------------------------");
			System.out.println("Your transfer has been processed.");
		}
		viewCurrentBalance();
	}

	private void requestFunds() {
		String token = currentUser.getToken();
		Transfer theTransfer = new Transfer();
		theTransfer.setTransfer_type_id(1);
		theTransfer.setTransfer_status_id(1);
		theTransfer.setAccount_to(accountService.getAccount(token).getAccount_id());
		AccountDTO[] allAccountDTOs = accountService.getAllAccountDTOs(token);
		printAccountDTOarray(allAccountDTOs);
		long fromUserId = (long)console.getUserInputInteger("Enter the user ID# to request funds from ");
		for(AccountDTO accountDTO: allAccountDTOs){
			if (accountDTO.getUser_id()== fromUserId){
				theTransfer.setAccount_from(accountDTO.getAccount_id());
				break;
			}
		}
		theTransfer.setAmount(console.getUserInputDouble("How much money would you like to request? "));
		Transfer attemptedTransfer = accountService.requestFunds(theTransfer, token);
		System.out.println();
		if(attemptedTransfer!=null) {
			System.out.println("--------------------------------------------------");
			System.out.println("       Your transfer request has been sent.       ");
			System.out.println("--------------------------------------------------\n");
		}
		viewCurrentBalance();
    }
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				exitProgram();
			}
		}
	}

	private void pendingTransferMenu() {
		while(isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(TRANSFER_MENU_OPTIONS);
			if (TRANSFER_MENU_OPTION_APPROVE.equals(choice)) {
				pendingTransferMenuChoicesApprove();
			} else if (TRANSFER_MENU_OPTION_REJECT.equals(choice)) {
				pendingTransferMenuChoicesReject();
			} else {
				break;
			}
		}
	}

	private void pendingTransferMenuChoicesApprove() {
		while(isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(TRANSFER_MENU_SELECT_APPROVE_OPTIONS);
			if (TRANSFER_MENU_SELECT_TRANSFER_APPROVE.equals(choice)) {
				long transfer_id = (long)console.getUserInputInteger("transfer ID# to approve");
				TransferPO transfer = accountService.getTransferPOByTransfer_Id(transfer_id, currentUser.getToken());
				if (transfer.getAmount() > accountService.getBalance(currentUser.getToken())) {
					System.out.println("--------------------------------------------------");
					System.out.println("Insufficient funds to complete this transfer.");
					System.out.println("--------------------------------------------------");
				}
				boolean approved = accountService.approvePendingFromTransfer(transfer_id, currentUser.getToken());
				if (approved){
					System.out.println("--------------------------------------------------");
					System.out.println("Transfer #" +transfer_id + " is approved");
					viewCurrentBalance();
					System.out.println("--------------------------------------------------");
				}
			} else {
				break;
			}
		}
	}

	private void pendingTransferMenuChoicesReject() {
		while(isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(TRANSFER_MENU_SELECT_REJECT_OPTIONS);
			if (TRANSFER_MENU_SELECT_TRANSFER_REJECT.equals(choice)) {
				long transfer_id = (long)console.getUserInputInteger("transfer ID# to reject");
				accountService.rejectPendingFromTransfer(transfer_id, currentUser.getToken());
			} else {
				break;
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
				System.out.println("--------------------------------------------------");
            	System.out.println(" ERROR: Must enter a valid username and password. ");            //+e.getMessage());
				System.out.println("         Please attempt to register again.        ");
				System.out.println("--------------------------------------------------\n");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null)
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("--------------------------------------------------");
		    	System.out.println("      ERROR:  Invalid username or password.       ");
				System.out.println("         Please attempt to login again.           ");
				System.out.println("--------------------------------------------------\n");
			}
		}
	}


	private void printTransferRequestArray(TransferPO[] transferPOs){
		System.out.println("--------------------------------------------------");
		System.out.println("      These transfers require your approval       ");
		System.out.println("--------------------------------------------------");
		System.out.println("                Pending Transfers                 ");
		System.out.println("  Transfer ID  |         To:         |   Amount   ");
		System.out.println("--------------------------------------------------");
		for(TransferPO transferPO: transferPOs){
			System.out.printf(transferPO.getTransfer_id() + "             To: %-17s $%.2f", transferPO.getTo_username(), transferPO.getAmount());
			System.out.println();
		}
		System.out.println("--------------------------------------------------\n");

	}

	private void printAccountDTOarray(AccountDTO[] accountDTOs){
		System.out.println("--------------------------------------------------");
		System.out.println("User");
		System.out.println("ID#          Name");
		System.out.println("--------------------------------------------------");
		for(AccountDTO accountDTO: accountDTOs){
			System.out.println(accountDTO.getUser_id()+"         " +accountDTO.getUsername());
		}
		System.out.println("--------------------------------------------------\n");
	}

	private void printFinalizedTransferPOarray(TransferPO[] transferPOs){
		System.out.println("--------------------------------------------------");
		System.out.println("             Your Finalized Transfers             ");
		System.out.println("  Transfer ID   |      To/From      |    Amount   ");
		System.out.println("--------------------------------------------------");
		for(TransferPO transferPO: transferPOs){
			if(transferPO.getFrom_username().equalsIgnoreCase(currentUser.getUser().getUsername())){
				System.out.printf(transferPO.getTransfer_id()+"              To: %-16s $%.2f", transferPO.getTo_username(), transferPO.getAmount());
				System.out.println();
			} else {
				System.out.printf(transferPO.getTransfer_id()+"              From: %-14s $%.2f", transferPO.getFrom_username(), transferPO.getAmount());
				System.out.println();
			}
		}
		System.out.println("--------------------------------------------------\n");
	}


	// FOR PRINTING THE DETAILS OF A SPECIFIC TRANSFER
	private void printTransfer(String token) {
    	Long transfer_id = console.getUserInputLong("Enter a transfer ID# to examine");
    	TransferPO transferPO = accountService.getTransferPOByTransfer_Id(transfer_id, token);

    	if (transferPO!=null) {
			System.out.println("--------------------------------------------------");
			System.out.println("                 Transfer Details                 ");
			System.out.println("--------------------------------------------------");
			System.out.println("Id:        " + transferPO.getTransfer_id());
			System.out.println("From:      " + transferPO.getFrom_username());
			System.out.println("To:        " + transferPO.getTo_username());
			System.out.println("Type:      " + transferPO.getTransfer_type_desc());
			System.out.println("Status:    " + transferPO.getTransfer_status_desc());
			System.out.printf("Amount:    $%.2f\n", transferPO.getAmount());
			System.out.println("--------------------------------------------------\n");
		} else {
    		System.out.println();
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}