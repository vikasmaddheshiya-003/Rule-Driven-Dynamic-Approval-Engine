package com.bank.dao;

import java.math.BigDecimal;
import java.net.Authenticator.RequestorType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.util.Clients;

import com.bank.db.DBConnection;
import com.bank.model.Request.RequestType;
import com.bank.model.TransactionHistory;
import com.bank.model.TransactionHistory.TransactionType;
import com.bank.model.TransactionHistory.TxnDirection;
import com.bank.model.TransactionHistory.Status;

public class TransactionDao {

	public List<TransactionHistory> getTransactionsByUser(long userId) throws SQLException {

		List<TransactionHistory> list = new ArrayList<>();

		String sql = "SELECT * FROM transaction_history WHERE user_id = ? ORDER BY created_at DESC ";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapTransaction(rs));
				}
			}
		}

		return list;
	}

	// 🔹 Get transactions between selected dates
	public List<TransactionHistory> getTransactionsByUserAndDate(long userId, java.util.Date from, java.util.Date to)
			throws SQLException {

		List<TransactionHistory> list = new ArrayList<>();

		String sql = "SELECT * FROM transaction_history WHERE user_id = ? AND DATE(created_at) BETWEEN ? AND ? ORDER BY created_at DESC ";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, userId);
			ps.setDate(2, new Date(from.getTime()));
			ps.setDate(3, new Date(to.getTime()));

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapTransaction(rs));
				}
			}
		}

		return list;
	}

	private TransactionHistory mapTransaction(ResultSet rs) throws SQLException {

		TransactionHistory tx = new TransactionHistory();

		tx.setTransactionId(rs.getLong("transaction_id"));
		tx.setRequestId(rs.getLong("request_id"));
		tx.setUserId(rs.getLong("user_id"));

		tx.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));

		tx.setTxnDirection(TxnDirection.valueOf(rs.getString("txn_direction")));

		tx.setAmount(rs.getBigDecimal("amount"));
		tx.setBalanceBefore(rs.getBigDecimal("balance_before"));
		tx.setBalanceAfter(rs.getBigDecimal("balance_after"));

		tx.setStatus(Status.valueOf(rs.getString("status")));

		tx.setCreatedAt(rs.getTimestamp("created_at"));

		return tx;
	}

	public void doTransaction(Long userId, RequestType reqType, BigDecimal amount) throws SQLException {

		String insertTxnSql = "INSERT INTO transaction_history "
				+ "(request_id, user_id, transaction_type, amount, balance_after) " + "VALUES (?, ?, ?, ?, ?)";

		String updateUserSql = "UPDATE users SET balance = ? WHERE user_id = ?";

		Connection con = null;

		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false); 


			UserDao userDao = new UserDao();
			BigDecimal currentBalance = userDao.getCurrentUserBalance();


			BigDecimal updatedBalance;

			if (reqType == RequestType.WITHDRAWAL) {
				if (currentBalance.compareTo(amount) < 0) {
					 Clients.showNotification(
						        "Insufficient Balance!!!!!!",
						        Clients.NOTIFICATION_TYPE_ERROR,
						        null,
						        "top_center",
						        3000
						    );
					return ; 
				}
				updatedBalance = currentBalance.subtract(amount);
			} else { 
				updatedBalance = currentBalance.add(amount);
			}


			try (PreparedStatement pst = con.prepareStatement(updateUserSql)) {

				pst.setBigDecimal(1, updatedBalance);
				pst.setLong(2, userId);
				pst.executeUpdate();
			}


			try (PreparedStatement pst = con.prepareStatement(insertTxnSql)) {

				pst.setLong(1, 0l); 
				pst.setLong(2, userId);
				pst.setString(3, reqType.name());
				pst.setBigDecimal(4, amount);
				pst.setBigDecimal(5, updatedBalance);

				pst.executeUpdate();
			}

			con.commit();

		} catch (Exception e) {
			if (con != null) {
				con.rollback(); 
			}
			throw new SQLException("Transaction failed", e);
		} finally {
			if (con != null) {
				con.setAutoCommit(true);
				con.close();
			}
		}
	}

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(200);
		BigDecimal b = new BigDecimal(300);
		System.out.println(a.subtract(b));

	}
}
