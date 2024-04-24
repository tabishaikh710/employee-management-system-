
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.LinkedList;
class RemoveEmployeeDialog extends JDialog {
    private JTextField idField;
    private JButton removeButton;

    public RemoveEmployeeDialog(Frame parent) {
        super(parent, "Remove Employee", true);
        setSize(300, 150);
        setLayout(new GridLayout(2, 2));

        idField = new JTextField();
        removeButton = new JButton("Remove");

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(RemoveEmployeeDialog.this,
                            "Please enter an Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                RemoveEmployee remove = new RemoveEmployee();
                remove.removeFile(id);

                dispose();
            }
        });

        add(new JLabel("Employee ID:"));
        add(idField);
        add(new JLabel());
        add(removeButton);
    }

    private class RemoveEmployee {
        public void removeFile(String id) {
            File file = new File("file" + id + ".txt");
            if (file.exists()) {
                if (file.delete()) {
                    JOptionPane.showMessageDialog(null, "Employee with ID " + id + " has been removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to remove employee with ID " + id + ". Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Employee with ID " + id + " does not exist.");
            }
        }
    }
}
class UpdateEmployeeDialog extends JDialog {
    private JTextField idField;
    private JTextField oldValueField;
    private JTextField newValueField;
    private JButton updateButton;

    public UpdateEmployeeDialog(Frame parent) {
        super(parent, "Update Employee", true);
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));

        idField = new JTextField();
        oldValueField = new JTextField();
        newValueField = new JTextField();
        updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String oldValue = oldValueField.getText();
                String newValue = newValueField.getText();

                if (id.isEmpty()|| oldValue.isEmpty() || newValue.isEmpty()) {
                    JOptionPane.showMessageDialog(UpdateEmployeeDialog.this,

                            "Please enter  complete information.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UpdateEmployee update = new UpdateEmployee();
                update.updateFile(id, oldValue, newValue);

                dispose();
            }
        });

        add(new JLabel("Employee ID:"));
        add(idField);
        add(new JLabel("Old Value:"));
        add(oldValueField);
        add(new JLabel("New Value:"));
        add(newValueField);
        add(new JLabel());
        add(updateButton);
    }

    private class UpdateEmployee {
        public void updateFile(String id, String oldValue, String newValue) {
            try {
                File file = new File("file" + id + ".txt");
                if (file.exists()) {
                    StringBuilder fileContent = new StringBuilder();
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains(oldValue)) {
                            line = line.replaceAll(oldValue, newValue);
                        }
                        fileContent.append(line).append("\n");
                    }
                    scanner.close();

                    FileWriter writer = new FileWriter(file);
                    writer.write(fileContent.toString());
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Employee information updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found!");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error occurred: " + e.getMessage());
            }
        }
    }
}
class ShowEmployeeDialog extends JDialog {
    private JTextField idField;
    private JButton showButton;

    public ShowEmployeeDialog(Frame parent) {
        super(parent, "Show Employee", true);
        setSize(300, 150);
        setLayout(new GridLayout(2, 2));

        idField = new JTextField();
        showButton = new JButton("Show");

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();

                //  form validation
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(ShowEmployeeDialog.this,
                            "Please enter  an ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ShowEmployee show = new ShowEmployee();
                show.viewFile(id);

                dispose();
            }
        });

        add(new JLabel("Employee ID:"));
        add(idField);
        add(new JLabel());
        add(showButton);



    }

    private class ShowEmployee {
        public void viewFile(String id) {
            try {
                File file = new File("file" + id + ".txt");
                if (file.exists()) {
                    Scanner scanner = new Scanner(file);
                    StringBuilder fileContent = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        fileContent.append(line).append("\n");
                    }
                    scanner.close();

                    JTextArea textArea = new JTextArea(fileContent.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Employee Information", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Employee not found!");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error occurred: " + e.getMessage());
            }
        }
    }
}

class AddEmployeeDialog extends JDialog {
    private JTextField nameField;
    private JTextField idField;
    private JTextField contactField;
    private JTextField salaryField;
    private JButton addButton;

    private LinkedList<String> list;

    public AddEmployeeDialog(Frame parent) {
        super(parent, "Add Employee", true);
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));

        nameField = new JTextField();
        idField = new JTextField();
        contactField = new JTextField();
        salaryField = new JTextField();
        addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String id = idField.getText();
                String contact = contactField.getText();
                String salary = salaryField.getText();


                //  form validation
                if (name.isEmpty() || id.isEmpty() || contact.isEmpty() || salary.isEmpty()) {
                    JOptionPane.showMessageDialog(AddEmployeeDialog.this,
                            "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                AddEmployee add = new AddEmployee(name, id, contact, salary);
                add.createFile();

                dispose();
            }
        });

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Employee ID:"));
        add(idField);
        add(new JLabel("Contact:"));
        add(contactField);
        add(new JLabel("Salary:"));
        add(salaryField);
        add(new JLabel());
        add(addButton);
    }

    private class AddEmployee {
        private String name;
        private String employeeId;
        private String contact;
        private String salary;

        AddEmployee(String name, String employeeId, String contact, String salary) {
            this.name = name;
            this.employeeId = employeeId;
            this.contact = contact;
            this.salary = salary;
        }

        public void createFile() {
            list = new LinkedList<>();
            list.add("Employee ID: " + employeeId);
            list.add("Employee Name: " + name);
            list.add("Contact: " + contact);
            list.add("Salary: " + salary);

            try {
                File file = new File("file" + employeeId + ".txt");
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(file);
                    for (String line : list) {
                        writer.write(line + "\n");
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Employee added successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Employee already exists!");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error occurred: " + e.getMessage());
            }
        }

    }
}
public class MainFrame extends JFrame {
    private JButton addButton;
    private JButton showButton;
    private JButton updateButton;
    private JButton removeButton;

    public MainFrame() {
        super("EMS");
        setSize(300, 200);
        setLayout(new GridLayout(2, 2));

        addButton = new JButton("Add Employee");
        showButton = new JButton("Show Employee");
        updateButton = new JButton("Update Employee");
        removeButton = new JButton("Remove Employee");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddEmployeeDialog addDialog = new AddEmployeeDialog(MainFrame.this);
                addDialog.setVisible(true);
            }
        });

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowEmployeeDialog showDialog = new ShowEmployeeDialog(MainFrame.this);
                showDialog.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateEmployeeDialog updateDialog = new UpdateEmployeeDialog(MainFrame.this);
                updateDialog.setVisible(true);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RemoveEmployeeDialog removeDialog = new RemoveEmployeeDialog(MainFrame.this);
                removeDialog.setVisible(true);
            }
        });

        add(addButton);
        add(showButton);
        add(updateButton);
        add(removeButton);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}



