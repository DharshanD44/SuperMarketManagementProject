package com.supermarketmanagement.api.Service;

import java.util.List;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;

public interface OrderLineDetailsService {

	Object updateLineOrderLineStatus(String status, List<Long> id);

	Object getOrderLineListDetails(CommonListRequestModel commonListRequestModel);

}
