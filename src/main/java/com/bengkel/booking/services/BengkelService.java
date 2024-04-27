package com.bengkel.booking.services;

import com.bengkel.booking.models.*;
import com.bengkel.booking.repositories.ItemServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BengkelService {
	
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
    private static Scanner input = new Scanner(System.in);
    private static Customer currentLoggedInCustomer;
	
	//Login
    public static void setCurrentLoggedInCustomer(Customer customer) {
        currentLoggedInCustomer = customer;
    }

	//Info Customer
    public static void showCustomerInfo() {
        if (currentLoggedInCustomer != null) {
            System.out.println("Informasi Customer/Profile:");
            System.out.println("Customer Id: " + currentLoggedInCustomer.getCustomerId());
            System.out.println("Nama: " + currentLoggedInCustomer.getName());
            System.out.println("Customer Status: " + (isMember(currentLoggedInCustomer) ? "Member" : "Non Member"));
            System.out.println("Alamat: " + currentLoggedInCustomer.getAddress());
            if (isMember(currentLoggedInCustomer)) {
                System.out.println("Saldo Koin: " + ((MemberCustomer) currentLoggedInCustomer).getSaldoCoin());
            }
            System.out.println();
            System.out.println("List Kendaraan: ");
            List<Vehicle> vehicles = currentLoggedInCustomer.getVehicles();
            if (!vehicles.isEmpty()) {
                PrintService.printVehicle(vehicles);
            } else {
                System.out.println("Belum ada kendaraan terdaftar.");
            }
        } else {
            System.out.println("Anda belum login.");
        }
    }

    private static boolean isMember(Customer customer) {
        if (customer instanceof MemberCustomer) {
            MemberCustomer memberCustomer = (MemberCustomer) customer;
            return memberCustomer.getSaldoCoin() > 0;
        } else {
            return false;
        }
    }
	
	//Booking atau Reservation
    public static void bookingService() {
        if (currentLoggedInCustomer == null) {
            System.out.println("Anda belum login.");
            return;
        }

        List<Vehicle> vehicles = currentLoggedInCustomer.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("Anda belum memiliki kendaraan terdaftar.");
            return;
        }
        PrintService.displayVehicleList(vehicles);

        System.out.print("Masukkan Vehicle Id: ");
        String vehicleId = input.nextLine();
        Vehicle selectedVehicle = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehiclesId().equals(vehicleId)) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Kendaraan dengan Vehicle Id tersebut tidak ditemukan.");
            return;
        }
        List<ItemService> availableServices = getItemServicesByVehicleType(selectedVehicle.getVehicleType(), ItemServiceRepository.getAllItemService());
        System.out.println("List Item Service untuk " + selectedVehicle.getVehicleType() + ":");
        PrintService.displayServiceList(availableServices);

        List<ItemService> selectedServices = new ArrayList<>();
        boolean addMoreService = true;
        while (addMoreService) {
            System.out.print("Silahkan masukkan Service Id: ");
            String serviceId = input.nextLine();
            ItemService selectedService = null;
            for (ItemService service : availableServices) {
                if (service.getServiceId().equalsIgnoreCase(serviceId)) {
                    selectedService = service;
                    break;
                }
            }

            if (selectedService == null) {
                System.out.println("Service Id tidak valid. Silakan coba lagi.");
                continue;
            }
            selectedServices.add(selectedService);

            System.out.print("Apakah anda ingin menambahkan Service Lainnya? (Y/T): ");
            String addMore = input.nextLine();
            if (!addMore.equalsIgnoreCase("Y")) {
                addMoreService = false;
            }
        }

        double totalAmount = 0;
        for (ItemService service : selectedServices) {
            totalAmount += service.getPrice();
        }
        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setCustomer(currentLoggedInCustomer);
        bookingOrder.setServices(selectedServices);
        bookingOrder.setTotalServicePrice(totalAmount);

        System.out.print("Silahkan Pilih Metode Pembayaran (Saldo Coin atau Cash): ");
        String paymentMethod = input.nextLine();
        bookingOrder.setPaymentMethod(paymentMethod);

        bookingOrder.calculatePayment();
        double paymentAmount = bookingOrder.getTotalPayment();
        System.out.println();
        System.out.println("Booking Berhasil!!!");
        System.out.println("Total Harga Service : " + totalAmount);
        System.out.println("Total Pembayaran : " + paymentAmount);
    }

    private static List<ItemService> getItemServicesByVehicleType(String vehicleType, List<ItemService> allServices) {
        List<ItemService> servicesByVehicleType = new ArrayList<>();
        for (ItemService service : allServices) {
            if (service.getVehicleType().equalsIgnoreCase(vehicleType)) {
                servicesByVehicleType.add(service);
            }
        }
        return servicesByVehicleType;
    }
	
	//Top Up Saldo Coin Untuk Member Customer
	
	//Logout
}
