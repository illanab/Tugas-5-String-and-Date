import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Proses Login
        boolean loginBerhasil = false;
        while (!loginBerhasil) {
            System.out.println("\n=== Log In ===");
            System.out.print("Username : ");
            String username = scanner.nextLine().trim();   // menyimpan input username dari pengguna
            System.out.print("Password : ");
            String password = scanner.nextLine().trim();   // menyimpan input password dari pengguna

            // Validasi Captcha
            String captchaGenerated = generateCaptcha();   // menyimpand captcha yang dihasilkan
            System.out.print("Captcha  : " + captchaGenerated + "\nMasukkan Captcha : ");
            String captchaInput = scanner.nextLine().trim();
                // perbandingan string untuk validasi log in
            if (username.equalsIgnoreCase("yaLamart") && password.equals("7890") && captchaInput.equals(captchaGenerated)) {
                System.out.println("Login berhasil!\n");
                loginBerhasil = true;
            } else {
                System.out.println("Login gagal. Silakan coba lagi.\n");
            }
        }
    
        while (true) {
            System.out.println("+--------------Selamat Datang di YALA MART------------+");
            String currentDateTime = getCurrentDateTime();             
            System.out.println("Tanggal dan Waktu : " + currentDateTime);              // menampilkan tanggal dan waktu saat ini
            System.out.println("+-----------------------------------------------------+");

            try {
                System.out.print("Masukkan No Faktur: ");
                String noFaktur = scanner.nextLine().trim(); // input no faktur

                System.out.print("Masukkan Kode Barang: ");
                String kodeBarang = scanner.nextLine().trim();  // input kode barang

                System.out.print("Masukkan Nama Barang: ");
                String namaBarang = scanner.nextLine().trim();   // input nama barang

                int hargaBarang = 0;
                while (true) {
                    try {
                        System.out.print("Masukkan Harga Barang: ");
                        hargaBarang = Integer.parseInt(scanner.nextLine().trim());
                        if (hargaBarang <= 0) {
                            throw new IllegalArgumentException("Harga barang harus lebih besar dari 0.");
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input harga barang tidak valid.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Kesalahan: " + e.getMessage());
                    }
                }

                int jumlahBeli = 0;
                while (true) {
                    try {
                        System.out.print("Masukkan Jumlah Beli: ");
                        jumlahBeli = Integer.parseInt(scanner.nextLine().trim());
                        if (jumlahBeli <= 0) {
                            throw new IllegalArgumentException("Jumlah beli harus lebih besar dari 0.");
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Input jumlah beli tidak valid.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Kesalahan: " + e.getMessage());
                    }
                }

                // Membuat objek Transaksi dan menghitung total
                Transaksi transaksi = new Transaksi(kodeBarang, namaBarang, hargaBarang, noFaktur, jumlahBeli);
                transaksi.hitungTotal();

                // Menampilkan invoice
                System.out.println("\n--- Faktur Pembelian ---");
                System.out.println(transaksi.displayInvoice());
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
                 break;
             }
        // Nama kasir
        String namaKasir = "Nabila ";  // nama kasir dalam format biasa
        // Menampilkan informasi kasir dengan format Title Case
        System.out.println("Kasir           : " + capitalizeWords(namaKasir)); // nama kasir
        System.out.println("+---------------------TERIMAKASI----------------------+");
       
    // Tanya ulang setelah input selesai
    System.out.print("\nApakah kamu ingin memasukkan data transaksi lain? (y/n): ");
            if (!konfirmasiUlang(scanner)) {
                System.out.println("Terima kasih.");
                break;
            }            
}
    scanner.close(); 
    }
// methode Captcha
    private static String generateCaptcha() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            captcha.append(characters.charAt(index));   // menambahkan karakter ke StringBuilder
        }
        return captcha.toString();     //mengembalikan hasil sebagai string
    }
// Method untuk mendapatkan tanggal dan waktu saat ini
    private static String getCurrentDateTime() {
        // Mendapatkan tanggal dan waktu sekarang
        LocalDateTime now = LocalDateTime.now();

        // Format tanggal dan waktu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Mengembalikan tanggal dan waktu dalam format yang diinginkan
        return now.format(formatter);
    }
    // Method untuk mengubah setiap kata menjadi Title Case
    private static String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("\\s+"); // Membagi input menjadi array kata
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                // Mengubah huruf pertama menjadi huruf besar, sisanya kecil
                capitalized.append(word.substring(0, 1).toUpperCase())
                           .append(word.substring(1).toLowerCase())
                           .append(" ");
            }
        }

        return capitalized.toString().trim(); // Menghapus spasi berlebih di akhir
    }
    
    // Method untuk meminta konfirmasi ulang (y/n)
    private static boolean konfirmasiUlang(Scanner scanner) {
        while (true) {
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                System.out.print("Input tidak valid. Masukkan 'y' atau 'n': ");
            }
        }
    }
    }