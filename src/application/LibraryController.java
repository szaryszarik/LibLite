package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.model.Author;
import application.model.Book;
import application.model.BookDetail;
import application.model.DbConnection;
import application.model.Loan;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibraryController implements Initializable
{
	//Search book tableView
	@FXML
	private TableView<Book> libraryTable;
	
	@FXML
	private TableColumn<Book, String> libraryIsbnColumn;
	
	@FXML
	private TableColumn<Book, String> libraryAuthorColumn;
	
	@FXML
	private TableColumn<Book, String> libraryTitleColumn;
	
	@FXML
	private TableColumn<Book, String> libraryCategoryColumn;
	
	@FXML
	private TextField bookText;
	
	private ObservableList<Book> data;
	private DbConnection dc; 
	private Connection conn;
	//
	
	//Search author tableView
	@FXML
	private TableView<Author> libraryTable2;
	
	@FXML
	private TableColumn<Author, String> libraryNameColumn;
	
	@FXML
	private TableColumn<Author, String> libraryBirthColumn;
	
	@FXML
	private TableColumn<Author, String> libraryCountryColumn;
	
	@FXML
	private TableColumn<Author, String> libraryCommentsColumn;
	
	@FXML
	private TextField authorText;
	
	private ObservableList<Author> data2; 
	//
	
	// Advanced search book tableView
	@FXML
	private TableView<BookDetail> advancedTable;
	
	@FXML
	private TableColumn<BookDetail, String> advancedIsbnCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedAuthorCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedTitleCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedCatCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedPublisherCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedDatPublCol;
	
	@FXML
	private TableColumn<BookDetail, Integer> advancedRatingCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedCommCol;
	
	@FXML
	private TableColumn<BookDetail, Integer> advancedOrderIdCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedOrderDtCol;
	
	@FXML
	private TableColumn<BookDetail, String> advancedLoanDtCol;
	
	@FXML
	private TextField advancedText;
	
	private ObservableList<BookDetail> dataAdvancedBook;
	
	
	//Advanced search user
	@FXML
	private TableView<User> advancedTable2;
	
	@FXML
	private TableColumn<User, String> userLoginCol;
	
	@FXML
	private TableColumn<User, String> userNameCol;
	
	@FXML
	private TableColumn<User, String> userAddressCol;
	
	@FXML
	private TableColumn<User, String> userPhoneCol;
	
	@FXML
	private TableColumn<User, String> userEmailCol;
	
	@FXML
	private TableColumn<User, String> userPeselCol;
	
	@FXML
	private TableColumn<User, Integer> userBooksCol;
	
	@FXML
	private TableColumn<User, Integer> userOrdersCol;
	
	@FXML
	private TableColumn<User, String> userStatusCol;
	
	@FXML
	private TextField advancedText2;
	
	private ObservableList<User> dataAdvancedUser;
	
	//Advanced search loan
	@FXML
	private TableView<Loan> advancedTable3;
	
	@FXML
	private TableColumn<Loan, String> loanBorrowerCol;
	
	@FXML
	private TableColumn<Loan, String> loanTitleCol;
	
	@FXML
	private TableColumn<Loan, String> loanIsbnrCol;
	
	@FXML
	private TableColumn<Loan, String> loanDateFromCol;
	
	@FXML
	private TableColumn<Loan, String> loanReturnToCol;
	
	@FXML
	private TableColumn<Loan, String> loanCommentsCol;
	
	@FXML
	private TextField advancedText3;
	
	private ObservableList<Loan> dataAdvancedLoan;
	
	@FXML
	private void initializeLibraryDB() throws SQLException{
		data = FXCollections.observableArrayList();
		
		String searchText = bookText.getText();
		
		String sql = "select distinct a.isbn, b.name, a.title, group_concat(c.category separator ', ')"
				+ " from tbl_book a, tbl_author b, tbl_category c"
				+ " where a.id_author = b.id"
				+ " and title like '%" + searchText + "%'" 
				+ " and (a.id_category_1 = c.id OR a.id_category_2 = c.id OR a.id_category_3 = c.id)"
				+ " group by a.isbn, b.name, a.title"; 
		
		//ResultSet rs = conn.createStatement().executeQuery(sql);
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(sql);
		
		while(rs.next()){
			data.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			System.out.println(rs.getString(1) + "|" + rs.getString(2));
		} 
		
		
		libraryIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		libraryAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
		libraryTitleColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		libraryCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		libraryTable.setItems(null);
		libraryTable.setItems(data); 
	}
	
	@FXML
	private void initializeAuthorDB() throws SQLException{
		data2 = FXCollections.observableArrayList();
		
		String searchText = authorText.getText();
		
		String sql = "select concat(name, ' ',surname) as name, birth_date, country, comments "
				+ "from tbl_author where concat(name, ' ',surname) like '%" + searchText + "%'";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getString(1) + "|" + rs.getString(2));
			data2.add(new Author(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		
		libraryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		libraryBirthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
		libraryCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		libraryCommentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));
		
		libraryTable2.setItems(null);
		libraryTable2.setItems(data2);
	}
	
	@FXML
	private void initializeAdvancedBookDB() throws SQLException{
		dataAdvancedBook = FXCollections.observableArrayList();
		
		String searchText = advancedText.getText();
		
		String sql = "select b.isbn, concat(a.name, ' ', a.surname) as author, b.title, " +
				   "group_concat(c.category separator ', ') as category, " +
			       "concat(p.name, ' ', p.second_name, ' ', p.organization) as publisher, " +
			       "b.date_of_publication, b.book_rating, b.comments, o.id, o.order_date, " +
			       "l.loan_date " +
			"from tbl_book b join tbl_author a on b.id_author = a.id " +
			"join tbl_category c on (b.id_category_1 = c.id " +
									 "OR b.id_category_2 = c.id " +
									 "OR b.id_category_3 = c.id) " +
			"left join tbl_publisher p on b.id_publisher = p.id " +
			"left join tbl_order o on b.id = o.book_id " +
			"left join tbl_loan l on b.id = l.book_id " +
			"where "   +
			"(category like '%" + searchText + "%' or b.isbn like '%" + searchText + "%' or concat(a.name, ' ', a.surname) like '%" + searchText + "%' " +
			 "or b.title like '%" + searchText + "%' or concat(p.name, ' ', p.second_name, ' ', p.organization) like '%" + searchText + "%' " +
			 "or b.book_rating like '%" + searchText + "%') group by 1,2,3,5,9,11";
		
		System.out.println(searchText);
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getString(3) + "|" + rs.getString(4));
			dataAdvancedBook.add(new BookDetail(rs.getString(1),rs.getString(2),rs.getString(4),rs.getString(3),
					rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8),rs.getInt(9),
					rs.getString(10),rs.getString(11)));
		} 
		
		advancedIsbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		advancedAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		advancedTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		advancedCatCol.setCellValueFactory(new PropertyValueFactory<>("category"));
		advancedPublisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		advancedDatPublCol.setCellValueFactory(new PropertyValueFactory<>("dateOfPublication"));
		advancedRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
		advancedCommCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
		advancedOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		advancedOrderDtCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		advancedLoanDtCol.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
		
		advancedTable.setItems(null);
		advancedTable.setItems(dataAdvancedBook);
	}
	
	@FXML
	private void initializeAdvancedUserDB() throws SQLException{
		dataAdvancedUser = FXCollections.observableArrayList();
		
		String searchText = advancedText2.getText();
		
		String sql = "select * from ("
				+ "select  u.username, concat(u.name, ' ',u.surname) name,"
				+ "concat(u.city, ' ',u.postal_code, ', ',u.street,' ',u.apartment_num) address,"
				+ "u.phone, u.email, u.socsecnumber,"
				+ "(select count(*) from tbl_loan l where l.user_id = u.id) books,"
				+ "(select count(*) from tbl_order o where o.user_id = u.id) orders,u.status "
				+ "from tbl_user u) x "
				+ "where x.username like '%" + searchText + "%' "
				+ "or x.name like '%" + searchText + "%' "
				+ "or x.socsecnumber like '%" + searchText + "%'";
		
		System.out.println(searchText);
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getString(3) + "|" + rs.getString(4));
			dataAdvancedUser.add(new User(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getString(9)));
		}
		
		userLoginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
		userNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		userAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		userPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		userPeselCol.setCellValueFactory(new PropertyValueFactory<>("pesel"));
		userBooksCol.setCellValueFactory(new PropertyValueFactory<>("books"));
		userOrdersCol.setCellValueFactory(new PropertyValueFactory<>("orders"));
		userStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		advancedTable2.setItems(null);
		advancedTable2.setItems(dataAdvancedUser);
	}
	
	@FXML
	private void initializeAdvancedLoanDB() throws SQLException{
		dataAdvancedLoan = FXCollections.observableArrayList();
		
		String searchText = advancedText3.getText();
		
		String sql = "select x.* from ("
				+ "select concat(u.name, ' ',u.surname) borrower, b.title, b.isbn,"
				+ "l.loan_date date_from, DATE_ADD(l.loan_date, INTERVAL 14 DAY) return_to, l.comments "
				+ "from tbl_loan l join tbl_user u on u.id = l.user_id "
				+ "join tbl_book b on b.id = l.book_id ) x "
				+ "where x.borrower like '%" + searchText + "%' "
				+ "or x.title like '%" + searchText + "%' "
				+ "or x.isbn like '%" + searchText + "%'";
		
		System.out.println(searchText);
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getString(3) + "|" + rs.getString(4));
			dataAdvancedLoan.add(new Loan(rs.getString(1),rs.getString(2),rs.getString(3),
					rs.getDate(4).toString(),rs.getDate(4).toString(),rs.getString(6)));
		}
		
		loanBorrowerCol.setCellValueFactory(new PropertyValueFactory<>("borrower"));
		loanTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		loanIsbnrCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		loanDateFromCol.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
		loanReturnToCol.setCellValueFactory(new PropertyValueFactory<>("returnTo"));
		loanCommentsCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
		System.out.println("wat");
		
		
		advancedTable3.setItems(null);
		advancedTable3.setItems(dataAdvancedLoan);
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dc = new DbConnection();
		conn = dc.connect();
	}
}
