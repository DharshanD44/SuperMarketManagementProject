package com.supermarketmanagement.api.daoImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.supermarketmanagement.api.Model.Custom.Users.UsersDetailsDto;
import com.supermarketmanagement.api.Model.Entity.UserModel;
import com.supermarketmanagement.api.Repository.UserDetailsRepository;
import com.supermarketmanagement.api.Util.WebServiceUtil;
import com.supermarketmanagement.api.dao.UserDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class UserDaoImp implements UserDao{
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserModel findByUserId(Long userId) {
		return userDetailsRepository.findById(userId).orElseThrow(
				()-> new RuntimeException(WebServiceUtil.USER_ID_NOT_FOUND+" "+userId)
				);
	}

	@Override
	public void saveUser(UserModel usermodel) {
		userDetailsRepository.save(usermodel);
	}

	@Override
	public List<UsersDetailsDto> getAllUsersDetails() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsersDetailsDto> criteriaQuery = builder.createQuery(UsersDetailsDto.class);
		Root<UserModel> root = criteriaQuery.from(UserModel.class);

		criteriaQuery.multiselect(
		        root.get("userId"),
		        root.get("username"),
		        root.get("password")
		);
		List<UsersDetailsDto> results = entityManager.createQuery(criteriaQuery).getResultList();
		return results;
	}

}
