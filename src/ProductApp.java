import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;


public class ProductApp extends JFrame {

    private final JTextField txtName = new JTextField(20);

    private final JComboBox<String> cmbProduct = new JComboBox<>(new String[]{
            "Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"
    });

    private final JTextField txtPrice = new JTextField(20);

    private final JButton btnProcess = new JButton("PROCESS");

    public ProductApp() {
        super("Product App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 5, 10));

        buildForm();
        buildMenu();

        btnProcess.addActionListener(e -> processSale());

        setSize(450, 220);
        setLocationRelativeTo(null);
    }

    private void buildForm() {
        add(new JLabel("Customer Name:"));
        add(txtName);
        add(new JLabel("Select Product:"));
        add(cmbProduct);
        add(new JLabel("Enter Price:"));
        add(txtPrice);
        add(new JLabel());
        add(btnProcess);
    }

    private void processSale() {
        String customerName = txtName.getText().trim();
        String productName = (String) cmbProduct.getSelectedItem();

        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer name.",
                    "Missing input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.",
                    "Invalid input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProductCalculations calc = new ProductCalculations(price);

        String amounts = "VAT: R " + calc.GetVat() + "\n"
                + "COMMISSION: R " + calc.GetCommission() + "\n"
                + "TOTAL: R " + calc.GetTotal();

        String message = "Customer Name: " + customerName + "\n"
                + "Product " + productName + "\n"
                + amounts;
        JOptionPane.showMessageDialog(this, message);

        String fileDetails = "Customer Name: " + customerName + "\n"
                + "Product: " + productName + "\n"
                + amounts;
        saveToFile(fileDetails);
    }

    private void saveToFile(String details) {
        try (FileWriter writer = new FileWriter("data.txt")) {
            writer.write("DATA LOG\n");
            writer.write("*".repeat(35) + "\n");
            writer.write(details + "\n");
            writer.write("*".repeat(35) + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save to data.txt.",
                    "File error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");
        processItem.addActionListener(e -> processSale());
        clearItem.addActionListener(e -> clearForm());
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }

    private void clearForm() {
        txtName.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}