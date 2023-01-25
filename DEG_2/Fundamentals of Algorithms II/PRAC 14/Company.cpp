#include <iostream>

#include "Company.h"

using namespace std;

// Linear cost O(1).
Company::Company() {

}

//O(1)
void Company::addShop(string shop) {
	if (!shopModelMap.exists(shop)) {
		Table<string, int> modelList;
		shopModelMap.insert(shop, modelList);
	}
}
/*
// It adds a shop to the company with a given name in the input parameter. If it
// already exists, it ignores the operation.
*/

// O(1).
void Company::addModel(string shop, string model, int n) {
	if (shopModelMap.exists(shop)) {
		Table<string, int> modelList = shopModelMap.get(shop);
		int num = 0;
		if (modelList.exists(model)) {
			num = modelList.get(model);
		}
		//modelList.remove(model);
		modelList.insert(model, num + n);
		shopModelMap.insert(shop, modelList);
	}
}

//It adds n cloth pieces of a new cloth model to a shop. If the shop
	//	already  had  this model, it  increments  the  number  of  pieces.If the  shop  does  not exist, it
	//	ignores the operation.

// Depends on the input n. Worst case scenario O(n), while best case is O(1).
List<string> Company::lastModels(string shop, int n) {
	List<string> res;
	if (shopModelMap.exists(shop)) {
		Table<string, int> tableModel = shopModelMap.get(shop);
		Table<string, int>::Iterator it = tableModel.begin();
		while (it != tableModel.end()) {
			res.push_back(it.key());
			it.next();
		}
	}
	return res;
}

//It returns a list of the “n” last models added to a given shop in inverse
	//  order.First, it should be the last model added.Second should be the second to the last model,
	//	and so on.If there are not enough last models, it only returns the available ones in this inverse

// O(1).
List<string> Company::shops() {
	List<string> res;
	SearchTree<string, Table<string, int>>::Iterator it = shopModelMap.begin();
	while (it != shopModelMap.end()) {
		res.push_back(it.key());
		it.next();
	}
	return res;
}
// It returns a list of the shops shorted alphabetically.

// O(1).
int Company::pieces(string shop, string model) {
	if (shopModelMap.exists(shop)) {
		Table<string, int> tableModel = shopModelMap.get(shop);
		if (tableModel.exists(model)) {
			return tableModel.get(model);
		}
		else {
			return 0;
		}
	}
	else {
		return 0;
	}
}
// It returns the number of pieces of cloth of a given shop and model. It
	//  returns 0 if either the model or the shop does not exist.