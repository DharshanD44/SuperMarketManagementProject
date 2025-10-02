package com.supermarketmanagement.api.Service;

import java.util.List;
import com.supermarketmanagement.api.Model.Custom.CommonListRequestModel;
import com.supermarketmanagement.api.Model.Custom.CommonMessageResponse;

public interface OrderLineDetailsService {

	Object updateLineOrderLineStatus(String status, List<Long> id);

	Object getOrderLineListDetails(CommonListRequestModel commonListRequestModel);

	CommonMessageResponse getOrderLineItemDetailsById(Long orderlineid);

}
