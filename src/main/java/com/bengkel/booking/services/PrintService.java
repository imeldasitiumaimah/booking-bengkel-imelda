package com.bengkel.booking.services;

import java.util.List;

import com.bengkel.booking.models.*;

public class PrintService {

	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";

		System.out.printf("%-25s %n", title);
		System.out.println(line);

		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			} else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}

	public static void printVehicle(List<Vehicle> listVehicle) {
		System.out.println("+----+-----------------+------------+-------+-----------------+");
		System.out.println("| No | Vehicle Id      | Warna      | Tahun | Tipe Kendaraan  |");
		System.out.println("+----+-----------------+------------+-------+-----------------+");
		int i = 1;
		for (Vehicle vehicle : listVehicle) {
			String type = (vehicle instanceof Car) ? "Mobil" : "Motor";
			System.out.printf("| %-2d | %-15s | %-10s | %-5s | %-15s |%n",
					i, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getYearRelease(), type);
			i++;
		}
		System.out.println("+----+-----------------+------------+-------+-----------------+");
	}

	public static void displayVehicleList(List<Vehicle> vehicles) {
		System.out.println("List Kendaraan Anda:");
		System.out.println("+----+-----------------+------------+-----------------+-------+");
		System.out.println("| No |    Vehicle Id   |   Warna    | Tipe Kendaraan  | Tahun |");
		System.out.println("+----+-----------------+------------+-----------------+-------+");
		for (int i = 0; i < vehicles.size(); i++) {
			Vehicle vehicle = vehicles.get(i);
			System.out.printf("| %-2d | %-15s | %-10s | %-15s | %-5s |%n",
					(i + 1), vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getVehicleType(), vehicle.getYearRelease());
		}
		System.out.println("+----+-----------------+------------+-----------------+-------+");
		System.out.println();
	}

	public static void displayServiceList(List<ItemService> availableServices) {
		System.out.println("+----+------------+----------------------+------------+");
		System.out.println("| No | Service Id |     Nama Service     |   Harga    |");
		System.out.println("+----+------------+----------------------+------------+");
		for (int i = 0; i < availableServices.size(); i++) {
			ItemService service = availableServices.get(i);
			System.out.printf("| %-2d | %-10s | %-20s | %-10s |%n",
					(i + 1), service.getServiceId(),
					service.getServiceName(), service.getPrice());
		}
		System.out.println("+----+------------+----------------------+------------+");
		System.out.println();
	}

	// Silahkan Tambahkan function print sesuai dengan kebutuhan.

}
