package com.bengkel.booking.services;

import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static Scanner input = new Scanner(System.in);
	private static Customer currentLoggedInCustomer;

	public static void run() {
		String[] listMenu = {"Login", "Exit"};
		boolean isLooping = true;
		do {
			PrintService.printMenu(listMenu, "Start Menu");
			int choice = Validation.validasiNumberWithRange("Masukan Pilihan Menu: ", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			switch (choice) {
				case 1:
					login();
					mainMenu();
					break;
				case 0:
					System.out.println("Terima kasih telah menggunakan aplikasi. Sampai jumpa!");
					isLooping = false;
					break;
				default:
					System.out.println("Pilihan tidak valid. Silakan pilih menu yang benar.");
			}
		} while (isLooping);
	}

	public static void login() {
		int attempt = 0;
		final int MAX_ATTEMPTS = 3;

		do {
			System.out.print("Masukan Customer Id: ");
			String customerIdInput = input.nextLine();

			System.out.print("Masukan Password: ");
			String passwordInput = input.nextLine();

			Customer customer = findCustomerById(customerIdInput);
			if (customer == null) {
				System.out.println("Customer Id Tidak Ditemukan atau Salah!");
				attempt++;
			} else if (!customer.getPassword().equals(passwordInput)) {
				System.out.println("Password yang Anda Masukkan Salah!");
				attempt++;
			} else {
				System.out.println("Login Berhasil!");
				currentLoggedInCustomer = customer;
				return;
			}

			if (attempt >= MAX_ATTEMPTS) {
				System.out.println("Anda telah melebihi batas percobaan login.");
				System.exit(0);
			}
		} while (true);
	}

	private static Customer findCustomerById(String customerId) {
		for (Customer customer : listAllCustomers) {
			if (customer.getCustomerId().equals(customerId)) {
				return customer;
			}
		}
		return null;
	}

	public static void mainMenu() {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			System.out.println();
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu: ", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				BengkelService.setCurrentLoggedInCustomer(currentLoggedInCustomer);
				BengkelService.showCustomerInfo();
				break;
			case 2:
				//panggil fitur Booking Bengkel
				BengkelService.setCurrentLoggedInCustomer(currentLoggedInCustomer);
				BengkelService.bookingService();
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				break;
			default:
				System.out.println("Logout");
				isLooping = false;
				break;
			}
		} while (isLooping);
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
