import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.List;
import javax.swing.JComboBox;
import java.net.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

public class WikiHow extends JFrame
        implements ActionListener {
    public String a = "!";
    JFrame frame = new JFrame("Test frame");
    Font font = new Font("Verdana", Font.PLAIN, 11);
    JMenuBar menuBar = new JMenuBar();
    JMenu theoryMenu = new JMenu("Теория");
    JMenuItem dictionaryTheoryItem = new JMenuItem("Словарь");
    JMenuItem lexicalTheoryItem = new JMenuItem("Лексика");
    JMenuItem alphabetTheoryItem= new JMenuItem("Алфавит");
    JMenuItem grammarTheoryItem = new JMenuItem("Грамматика");
    JMenu testMenu = new JMenu("Тест");
    JMenuItem rusLexTestItem = new JMenuItem("Лексика(кор-рус)");
    JMenuItem korLexTestItem = new JMenuItem("Лексика(рус->кор)");
    JMenuItem grammarTestItem = new JMenuItem("Грамматика");
    JMenu helpMenu = new JMenu("Справка");
    JMenuItem helpHelpItem = new JMenuItem("Справка");
    JMenuItem aboutHelpItem = new JMenuItem("О программе");
    JMenuItem updateHelpItem = new JMenuItem("Обновить");
    JButton queryButton = new JButton("Поиск");
    JButton prev = new JButton("Пред.тема");
    JButton next = new JButton("След.тема");
    JButton prevLex = new JButton("Пред.тема");
    JButton nextLex = new JButton("След.тема");
    JButton prevTest = new JButton("Пред.тема");
    JButton nextTest = new JButton("След.тема");
    JButton prevGramTest = new JButton("Пред.тема");
    JButton nextGramTest = new JButton("След.тема");
    JLabel testNum = new JLabel("тест №"); //Метка для вывода номера теста
    JTextArea myText = new JTextArea("");// Текстовое многострочное поле.
    JScrollPane scrollPane = new JScrollPane();
    JPanel holdAll = new JPanel(); //Родительская панель, содержащая все.
    JTextField wordInput = new JTextField();
    JPanel queryPanel = new JPanel();
    JPanel bottomGrammarPanel = new JPanel();
    JPanel bottomLexPanel = new JPanel();
    JPanel bottomTestPanel = new JPanel();
    JPanel bottomGramTestPanel = new JPanel();
    String[] item = { //Значения для ComboBox
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    };
    JComboBox gramComboBox = new JComboBox(item);
    JComboBox lexComboBox = new JComboBox(item);
    JComboBox lexTestComboBox = new JComboBox(item);
    JComboBox gramTestComboBox = new JComboBox(item);

    /**
     * Конструктор.
     */
    DefaultTableModel tableModel;

    public WikiHow() throws Exception {

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("Корейское значение");
        tableModel.addColumn("Русское значение");
        tableModel.addColumn("Часть речи");
        tableModel.addColumn("Тема");
        bottomGrammarPanel.setLayout(new FlowLayout());
        bottomGrammarPanel.add(prev);
        bottomGrammarPanel.add(next);
        bottomLexPanel.add(prevLex);
        bottomLexPanel.add(nextLex);
        bottomTestPanel.add(prevTest);
        bottomTestPanel.add(nextTest);
        bottomTestPanel.add(lexTestComboBox);
        bottomTestPanel.add(gramTestComboBox);
        bottomGramTestPanel.add(prevGramTest);
        bottomGramTestPanel.add(nextGramTest);
        bottomGramTestPanel.add(gramTestComboBox);
        holdAll.add(menuBar);
        menuBar.add(theoryMenu);
        theoryMenu.add(dictionaryTheoryItem);
        theoryMenu.add(lexicalTheoryItem);
        theoryMenu.add(alphabetTheoryItem);
        theoryMenu.add(grammarTheoryItem);
        menuBar.add(testMenu);
        testMenu.add(rusLexTestItem);
        testMenu.add(korLexTestItem);
        testMenu.add(grammarTestItem);
        menuBar.add(helpMenu);
        helpMenu.add(helpHelpItem);
        helpMenu.add(aboutHelpItem);
        helpMenu.add(updateHelpItem);
        holdAll.setLayout(new BorderLayout());
        gramComboBox.setEditable(true);
        lexComboBox.setEditable(true);
        lexTestComboBox.setEditable(true);
        gramComboBox.setEditable(true);
        gramTestComboBox.setEditable(true);
        bottomGrammarPanel.add(gramComboBox);
        bottomLexPanel.add(lexComboBox);
        queryPanel.add(wordInput);
        queryPanel.add(queryButton);
        holdAll.add(queryPanel, BorderLayout.NORTH);
        holdAll.add(scrollPane, BorderLayout.CENTER);
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.X_AXIS));//горизонтальная панель запроса
        scrollPane.getViewport().add(table);
        getContentPane().add(holdAll);
        dictionaryTheoryItem.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);
        gramComboBox.addActionListener(this);
        lexTestComboBox.addActionListener(this);
        gramTestComboBox.addActionListener(this);
        prevLex.addActionListener(this);
        nextLex.addActionListener(this);
        lexComboBox.addActionListener(this);
        grammarTheoryItem.addActionListener(this);
        alphabetTheoryItem.addActionListener(this);
        lexicalTheoryItem.addActionListener(this);
        rusLexTestItem.addActionListener(this);
        korLexTestItem.addActionListener(this);
        queryButton.addActionListener(this);
        prevTest.addActionListener(this);
        nextTest.addActionListener(this);
        prevGramTest.addActionListener(this);
        nextGramTest.addActionListener(this);
        grammarTestItem.addActionListener(this);
        helpHelpItem.addActionListener(this);
        aboutHelpItem.addActionListener(this);
        updateHelpItem.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(menuBar);
        // Указываем, где оно должно появиться:
      this.setLocation(20, 20);
      this.setSize(700, 700);
      setTitle("Easy korean");
      this.setVisible(true); // Показать!
    }

    /**
     * Программа
     *
     * @param args Параметры старта программы, не используются.
     */
    public static void main(String[] args)
            throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        WikiHow myApplication = new WikiHow();
    }

    /**
     * Любой неабстрактный класс, который реализует ActionListener
     * должен иметь этот метод.
     *
     * @param e Событие.
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == dictionaryTheoryItem) { //"Словарь"
            {
                try {
                    setTitle("Словарь");
                    holdAll.removeAll(); //удалить все с главной панели
                    holdAll.setLayout(new BorderLayout());
                    queryPanel.add(wordInput);
                    queryPanel.add(queryButton);
                    holdAll.add(queryPanel, BorderLayout.NORTH);//добавить панель запроса
                    tableModel = new DefaultTableModel();
                    JTable table = new JTable(tableModel);
                    tableModel.addColumn("Корейское значение"); //заголовки таблицы
                    tableModel.addColumn("Русское значение");
                    tableModel.addColumn("Часть речи");
                    tableModel.addColumn("Тема");
                    holdAll.add(scrollPane, BorderLayout.CENTER);
                    scrollPane.getViewport().add(table);
                    gram.query = "select * from dictionary";  //переменная, содержащая текст запроса к БД
                    vivod();    //функция вывода словаря
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        else if (e.getSource() == queryButton) { //функция для кнопки поиска слов
            try {
                vivodPoisk(); //вызов функции поиска слов в словаре
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == lexicalTheoryItem) {  // "Теория" ->"Лексика"
            try {
                holdAll.removeAll();
                holdAll.setLayout(new BorderLayout());
                holdAll.add(bottomLexPanel, BorderLayout.SOUTH);
                setTitle("Учебник по лексике");
                queryPanel.add(wordInput);
                queryPanel.add(queryButton);
                holdAll.add(queryPanel, BorderLayout.NORTH); //панель запроса
                tableModel = new DefaultTableModel();
                JTable table = new JTable(tableModel);
                tableModel.addColumn("Корейское значение");
                tableModel.addColumn("Русское значение");
                tableModel.addColumn("Часть речи");
                tableModel.addColumn("Тема");
                holdAll.add(scrollPane, BorderLayout.CENTER);
                scrollPane.getViewport().add(table); //добавление таблицы
                gram.themeNum = 1;  //номер темы
                gram.query = "select * from dictionary where theme =" + gram.themeNum; //переменная, содержащая текст запроса к БД
                vivod();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == nextLex) {
            try {
                tableModel.setRowCount(0);
                if (gram.themeNum < 10) {
                    gram.themeNum++; //инкремент номера темы
                }
                gram.query = "select * from dictionary where theme =" + gram.themeNum;
                vivod();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == prevLex) {
            try {
                tableModel.setRowCount(0);
                if (gram.themeNum > 1) {
                    gram.themeNum--; //декремент номера темы
                }
                gram.query = "select * from dictionary where theme =" + gram.themeNum;
                vivod();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == lexComboBox) { //функция выбора темы  в разделе "Лексика" .!!!!!!!!!!!!!!!!!!!
            try {
                tableModel.setRowCount(0);
                gram.themeNum = lexComboBox.getSelectedIndex()+1;//выбранный номер темы
                gram.query = "select * from dictionary where theme =" + gram.themeNum;
                vivod();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


        else if (e.getSource() == alphabetTheoryItem) { //"Алфавит"
           try {
                JFrame frame = new JFrame("Корейский алфавит и правила чтения");
                JEditorPane editorPane = new JEditorPane();
                editorPane.setContentType("text/html;charset=UTF-8");  //set content as html
                editorPane.setEditorKit(new HTMLEditorKit());
                String filename = "index.htm"; //имя файла с главной страницей справочника по алфавиту и чтению
                editorPane.setEditable(false);
                FileReader reader = new FileReader(filename);

               Charset charset = Charset.forName("UTF-8");
               BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(filename), charset));

                editorPane.read(reader2, filename);
                JScrollPane scrollPane = new JScrollPane(editorPane);

                editorPane.addHyperlinkListener(new HyperlinkListener() { //функция реакции на нажатие гиперссылки
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            String filename = e.getDescription();
                            try {
                                Charset charset = Charset.forName("UTF-8");
                                BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(filename), charset));
                                editorPane.read(reader2, filename);
                                frame.revalidate(); //проверить форму
                                frame.repaint(); //перерисовать форму
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.setSize(800, 650); //размер окна
                frame.setVisible(true);  //сделать видимым
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, e1);
            }
        }

        else if (e.getSource() == grammarTheoryItem) { //"Теория"->"Грамматика"
            try {
                holdAll.removeAll();
                setTitle("Учебник по грамматике");
                holdAll.setLayout(new BorderLayout());
                holdAll.add(bottomGrammarPanel, BorderLayout.NORTH); //панель с кнопками
                scrollPane.getViewport().add(myText, BorderLayout.CENTER);
                myText.setMaximumSize(new Dimension(500, 500)); //максимальный размер текстового поля
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//вертик.прокрутка- всегда
                holdAll.add(scrollPane, BorderLayout.CENTER);//полоса прорутки
                gram.themeNum = 1; //номер темы=1
                gram.query = "select * from grammar where theme = " + gram.themeNum; //текст запроса к базе
                grammar(); // Функция вывода информации по грамматике
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == next) { // функция для кнопки След.тема в разделе "Грамматика"
            try {
                holdAll.removeAll();
                holdAll.setLayout(new BorderLayout());
                myText.setText("");
                scrollPane.getViewport().add(myText, BorderLayout.CENTER);
                holdAll.add(scrollPane, BorderLayout.CENTER);
                holdAll.add(bottomGrammarPanel, BorderLayout.NORTH);
                if (gram.themeNum < 10) {
                    gram.themeNum++;
                }
                grammar(); // Функция вывода информации по грамматике
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == prev) { // функция для кнопки Пред.тема в разделе "Грамматика"
            try {
                holdAll.removeAll();
                holdAll.setLayout(new BorderLayout());
                myText.setText("");
                scrollPane.getViewport().add(myText, BorderLayout.CENTER);
                holdAll.add(scrollPane, BorderLayout.CENTER);
                holdAll.add(bottomGrammarPanel, BorderLayout.NORTH);
                if (gram.themeNum > 1) {
                    gram.themeNum--; //декремент номера темы
                }
                grammar(); // Функция вывода информации по грамматике
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == gramComboBox) { //функция выбора темы  в разделе "Грамматика"
            try {
                holdAll.removeAll();
                holdAll.setLayout(new BorderLayout());
                scrollPane.getViewport().add(myText, BorderLayout.CENTER);
                holdAll.add(scrollPane, BorderLayout.CENTER);
                holdAll.add(bottomGrammarPanel, BorderLayout.NORTH); //панель с кнопками
                gram.themeNum = gramComboBox.getSelectedIndex()+1; //выбранный номер темы
                        grammar(); // Функция вывода информации по грамматике
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


        else if (e.getSource() == rusLexTestItem) { // Тест ->"Лексика(кор.->рус.)"
            try {
                setTitle("Тест по лексике");
                RowData.lang = 4;   //номер столбца с корейским значением слов (вопросы)
                RowData.lang2 = 3;  //номер столбца с русским значением слов (ответы)
                gram.query = "select * from dictionary where korWord = '"; //переменная, содержащая часть запроса к БД
                gram.themeNum = 1; //номер темы
                check();  //функция проверки ответов
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == korLexTestItem) { // функция для кнопки "Тест по лексике"
            try {
                setTitle("Тест по лексике");
                RowData.lang = 3; //номер столбца с корейским значением слов (вопросы)
                RowData.lang2 = 4;  //номер столбца с русским значением слов (ответы)
                gram.query = "select * from dictionary where rusWord = '"; //переменная, содержащая часть запроса к БД
                gram.themeNum = 1; //номер темы
                check(); //функция проверки ответов

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == nextTest) { // "След.тема" (Тест по лексике)
            try {
                setTitle("Тест по лексике");
                gram.themeNum++;//инкремент номера темы
                check(); //функция проверки ответов

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == prevTest) { // "Пред.тема" (Тест по лексике)
            try {
                setTitle("Тест по лексике");
                if (gram.themeNum > 1) {
                    gram.themeNum--; //декремент номера темы
                    check(); //функция проверки ответов
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == lexTestComboBox) { // "Пред.тема" (Тест по лексике)
            try {
                setTitle("Тест по лексике");
                gram.themeNum = lexTestComboBox.getSelectedIndex()+1; //выбранный номер темы
                check(); //функция проверки ответов
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


        else if (e.getSource() == grammarTestItem) { // "Тест" -> "Грамматика"
            try {
                setTitle("Тест по грамматике");
                RowData.lang = 4; //номер столбца с вопросом
                RowData.lang2 = 5; //номер столбца с ответом
                gram.query = "select * from gramTest where question = '"; //переменная, содержащая часть запроса к БД
                gram.themeNum = 1; // номер темы = 1
                checkGram(); // вывод теста
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == nextGramTest) { // "След. тема" - тест по грамматике
            try {
                setTitle("Тест по грамматике");
                gram.themeNum++; //инкремент номера темы
                checkGram(); // вывод теста
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == prevGramTest) { // "Пред. тема" - тест по грамматике
            try {
                setTitle("Тест по грамматике");
                if (gram.themeNum > 1) {
                    gram.themeNum--; //декремент номера темы
                    checkGram(); // вывод теста
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == gramTestComboBox) { // Выбрать тему - тест по грамматике
            try {
                setTitle("Тест по грамматике");
                    gram.themeNum = gramTestComboBox.getSelectedIndex()+1; //выбранный номер темы
                checkGram(); // вывод теста
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == aboutHelpItem) { //"О программе"
            try {
                JOptionPane.showMessageDialog(frame, //Диалоговое окно с информацией
                        "(c) Казанцева Алена, 2015. " +
                                "\n" +   "Сайт программы: http://easykorean.16mb.com" +
                                "\n" +     "Программа для изучения корейского языка начального (1) уровня. \n" +
                                "\n" +  "Теоретический материал для программы взят из учебника \"Корейский язык. Начальный уровень\" Ли Н., Пак Х. ",
                        "О программе",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() ==helpHelpItem) { // "Справка"
            try {
                File file=new File("help.chm"); //имя файла, содержащего справочный материал
                Desktop.getDesktop().open(file); //открытие файла справки
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == updateHelpItem) { //"Обновить".
            try {
               update("sqlite.db3","http://easykorean.16mb.com/sqlite.db3");//Вызов функции обновления БД программы
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    static class Baza { //Класс для функций подключения к БД
        static Statement st;
    }

    public void connectionBD() // Функция подключения к БД, к таблице dictionary
            throws Exception {
                try {//Этот код не удалять
                    Class.forName("org.sqlite.JDBC");
                    Connection bd = DriverManager.getConnection("jdbc:sqlite:sqlite.db3");//подключение к БД sqlite.db3
                    Baza.st = bd.createStatement();
                    Baza.st.execute("create table if not exists 'dictionary' ( 'num' int,'theme' int,'rusWord' text, 'korWord' text, 'part_of_speech' int);\");\t);");
                    //создаем таблицу dictionary
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }

    }

    public void vivod() //функция вывода словаря
            throws Exception {
        connectionBD(); //Вызов функции подключения к БД, к таблице dictionary
        ResultSet rs = Baza.st.executeQuery(gram.query);
        while (rs.next()) {
            a = rs.getString(5); // a = Part_of_speech, т.е. часть речи. В БД содержится номер части речи.

            String Part_of_speech;
            switch (a) {
                //Перевод части речи из численного представления в символьное для вывода на экран
                //т.е. 1->"сущ.", 2->глаг.", 3->числ.,4->нар./служ.
                case "2":
                    Part_of_speech = "глаг.";
                    break;
                case "3":
                    Part_of_speech = "числ.";
                    break;
                case "4":
                    Part_of_speech = "нар./служ.";
                    break;
                default:
                    Part_of_speech = "сущ.";
                    break;
            }
            tableModel.addRow(new Object[]{rs.getString(4), rs.getString(3), Part_of_speech, rs.getString(2)});
            //Таблица на экране со след. столбцами: /Корейское значение/ Русское значение/ Часть речи/ Тема/
            holdAll.revalidate(); //проверить форму
            holdAll.repaint();    //перерисовать форму
        }
    }

    public void vivodPoisk()
            throws Exception {
        connectionBD(); //Вызов функции подключения к БД, к таблице dictionary
        String query = "select * from dictionary where korWord like \"" + wordInput.getText() + "\"" + "or rusWord like \"" + wordInput.getText() + "\"";
        ResultSet rs = Baza.st.executeQuery(query);
        tableModel.setRowCount(0);

        while (rs.next()) {
            String a = rs.getString(5);
            String Part_of_speech;
            switch (a) {
                //Перевод части речи из численного представления в символьное для вывода на экран
                //т.е. 1->"сущ.", 2->глаг.", 3->числ.,4->нар./служ.
                case "2":
                    Part_of_speech = "глаг.";
                    break;
                case "3":
                    Part_of_speech = "числ.";
                    break;
                case "4":
                    Part_of_speech = "нар./служ.";
                    break;
                default:
                    Part_of_speech = "сущ.";
                    break;
            }
            //Таблица на экране со след. столбцами: /Корейское значение/ Русское значение/ Часть речи/ Тема/
            //Добавление ряда в таблицу
            tableModel.addRow(new Object[]{rs.getString(4), rs.getString(3), Part_of_speech, rs.getString(2)});
        }
        if (tableModel.getRowCount() == 0) {
            tableModel.addRow(new Object[]{"слово не найдено!"});//Если слово в словаре не найдено, выводится сообщение
        }
    }

    static class RowData { //статический класс
        String word; //вопрос
        JTextField answer; //введенный пользователем ответ на вопрос теста
        JLabel result; //результат, т.е. "+" при правильном ответе, сам либо правильный ответ
        static int m; //оценка
        static int lang; //язык вопросов (точнее, номер столбца с нужным языком)
        static int lang2; //язык ответов (точнее, номер столбца с нужным языком)
        static String slovo; //вопрос
        public RowData(String word, JTextField answer, JLabel result) {
            this.word = word;
            this.answer = answer;
            this.result = result;
        }
    }

    List<RowData> rowData; //создание списка

    public void check() //функция проверки тестов по лексике
            throws Exception {
        rowData = new ArrayList<>();
        connectionBD(); //Вызов функции подключения к БД, к таблице dictionary
        ResultSet rs = Baza.st.executeQuery("select * from dictionary where theme=" + gram.themeNum + " ORDER BY RANDOM() LIMIT 20");
        holdAll.removeAll();
        holdAll.setLayout(new GridBagLayout());
        int count = 1;
        while (rs.next()) {
            RowData.slovo = rs.getString(RowData.lang);

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;  //номер столбца
            c.gridy = count; //номер строки

            JLabel label = new JLabel(count + ") " + RowData.slovo);
            c.anchor = GridBagConstraints.WEST;
            holdAll.add(label, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;  //номер столбца
            c.weightx = 1; //ширина столбца
            c.gridy = count; //номер строки
            JTextField answer = new JTextField();
            holdAll.add(answer, c);

            c.fill = GridBagConstraints.NONE;
            c.gridx = 2;  //номер столбца
            c.weightx = 0; //ширина столбца
            c.gridy = count; //номер строки

            JLabel result = new JLabel("Результат: ");
            c.anchor = GridBagConstraints.WEST;
            holdAll.add(result, c);
            count++;
            rowData.add(new RowData(RowData.slovo, answer, result));
        }

        JButton endTest = new JButton("Закончить тест"); // кнопка "Закончить тест"

        endTest.addActionListener(a -> { //обработка нажатия кнопки  "Закончить тест"
            RowData.m = 0;

            rowData.forEach(data -> {
                String query = gram.query + data.word + "'";
                try {
                    Baza.st.executeQuery(query);

                    if (data.answer.getText().equals(rs.getString(RowData.lang2))) { //сравнить введенный ответ с ответом из БД
                        data.result.setText("+"); // рядом с правильными ответами ставится знак "+"
                        RowData.m++; //инкремент оценки за тест
                    } else {
                        data.result.setText(rs.getString(RowData.lang2)); //рядом с неправильным ответом пишется правильный
                    }//пишем правильный ответ

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            JOptionPane.showMessageDialog(frame, //Диалоговое окно с оценкой за тест по 20-ти бальной шкале
                    "Оценка: " + RowData.m + " из 20.",
                    "Результат теста",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;  //номер столбца
        c.weightx = 1; //ширина столбца
        c.gridwidth = 3; // ширина кнопки - 3 ячейки по горизонтали
        c.gridy = count; //номер строки
        holdAll.add(endTest, c);//добавление кнопки "Закончить тест"
        holdAll.add(bottomTestPanel);
        holdAll.add(testNum); //Метка для вывода номера теста
        testNum.setText("Тест № " + gram.themeNum); //Метка для вывода номера теста
        holdAll.revalidate(); //проверить форму
        holdAll.repaint();    //перерисовать форму
    }

    public void checkGram()
            throws Exception {
        rowData = new ArrayList<>();
        connectionGrammarBD(); //Вызов функции подключения к БД, к таблице Grammar
        ResultSet rs = Baza.st.executeQuery("select * from gramTest where theme=" + gram.themeNum + " LIMIT 20");
        holdAll.removeAll();
        holdAll.setLayout(new GridBagLayout());
        int count = 1;
        while (rs.next()) {
            RowData.slovo = rs.getString(RowData.lang);

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;  //номер столбца
            c.gridy = count; //номер строки

            JLabel label = new JLabel(count + ") " + RowData.slovo);//rs.getString(4));
            c.anchor = GridBagConstraints.WEST;
            holdAll.add(label, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;  //номер столбца
            c.weightx = 1; //ширина столбца
            c.gridy = count; //номер строки
            JTextField answer = new JTextField();
            holdAll.add(answer, c);

            c.fill = GridBagConstraints.NONE;
            c.gridx = 2;  //номер столбца
            c.weightx = 0; //ширина столбца
            c.gridy = count; //номер строки

            JLabel result = new JLabel("Результат: ");
            c.anchor = GridBagConstraints.WEST;
            holdAll.add(result, c);
            count++;
            rowData.add(new RowData(RowData.slovo, answer, result));
        }

        JButton endTest = new JButton("Закончить тест"); //добавление кнопки "Закончить тест"

        endTest.addActionListener(a -> { //обработка нажатия кнокпи "Закончить тест"
            RowData.m = 0;

            rowData.forEach(data -> {
                String query = gram.query + data.word + "'";
                try {
                    Baza.st.executeQuery(query);

                    if (data.answer.getText().equals(rs.getString(RowData.lang2))) { //сравниваем введенный ответ с ответом из БД
                        data.result.setText("+"); // рядом с правильными ответами ставится знак "+"
                        RowData.m++; //инкремент оценки за тест
                    } else {
                        data.result.setText(rs.getString(RowData.lang2));//рядом с неправильным ответом пишется правильный
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            JOptionPane.showMessageDialog(frame,  //Диалоговое окно с информацией
                    "Правильных ответов: " + RowData.m,
                    "Результат теста",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; //номер столбца
        c.weightx = 1; //ширина столбца
        c.gridwidth = 3; // ширина кнопки - 3 ячейки по горизонтали
        c.gridy = count; //номер строки
        holdAll.add(endTest, c);//добавление кнопки "Закончить тест"
        holdAll.add(bottomGramTestPanel); //панель с кнопками
        holdAll.add(testNum);
        c.gridy = 0; //номер строки
        testNum.setText("Тест № " + gram.themeNum + ". Составьте предложения. Раскройте скобки. Вместо ''__'' вставьте слова.");//Номер теста и задание
        holdAll.revalidate(); //проверить форму
        holdAll.repaint();    //перерисовать форму
    }

    static class gram { //статический класс для теории по грамматике
        static int themeNum; //номер темы
        static String query; //текст запроса к БД
    }

    public void connectionGrammarBD() // Функция подключения к БД, к таблице Grammar
            throws Exception {
        try {
        Class.forName("org.sqlite.JDBC");
        Connection bd = DriverManager.getConnection("jdbc:sqlite:sqlite.db3");//подключение к БД sqlite.db3
        Baza.st = bd.createStatement();
        Baza.st.execute("create table if not exists 'grammar' ( 'num' int,'theme' int,'title' text, 'rusText' text, 'korText' text, 'example' text);");
        //создаем таблицу dictionary
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }

    public void grammar() // функция вывода теории по грамматике
            throws Exception {
        connectionGrammarBD(); //Вызов функции подключения к БД, к таблице Grammar
        gram.query = "select * from grammar where theme = " + gram.themeNum;//запрос к БД
        ResultSet rs = Baza.st.executeQuery(gram.query);

        while (rs.next()) {
            myText.append(rs.getString(1) + "."); //добавление номера темы
            myText.append(rs.getString(2) + " "); //добавление номера правила в теме
            myText.append(rs.getString(3) + " "); //добавление заголовка темы
            myText.append("\n");
            myText.append(rs.getString(4) + " "); //добавление пояснения к теме на русском языке
            myText.append("\n");
            myText.append(rs.getString(5) + " "); //добавление грамматической формулы
            myText.append("\n");
            myText.append(rs.getString(6) + " "); //добавление примеров
            myText.append("\n");
            myText.append("-----------------------------------------------------------");
            myText.append("\n");
        }
        holdAll.revalidate(); //проверить форму
        holdAll.repaint();    //перерисовать форму
    }

    //    url = "http://easykorean.16mb.com/sqlite.db3";
    public void update(final String filename, final String urlString) //Функция обновления БД через интернет
            throws IOException {
        BufferedInputStream in = null; //Входной поток данных
        FileOutputStream fout = null;  //Выходной поток данных
        in = new BufferedInputStream(new URL(urlString).openStream());
        byte data[] = new byte[1024];
        int count;
        while((count = in.read(data,0,1024)) != -1)
        {
            System.out.write(data, 0, count);
        }
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename); //вывод выходного потока в файл БД
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close(); //закрыть входной файл
            }
            if (fout != null) {
                fout.close(); //закрыть выходной файл
            }
        }
    }
}