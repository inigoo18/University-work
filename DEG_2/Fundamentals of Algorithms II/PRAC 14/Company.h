#ifndef COMPANY_H_
#define COMPANY_H_

#include "List.h"
#include "Table.h"
#include "SearchTree.h"
#include <string>

class Company {

public:
	Company(); // empty builder

	void addShop(std::string shop); // It adds a shop to the company with a given name in the input parameter. If it
	// already exists, it ignores the operation.

	void addModel(std::string shop, std::string model, int n); //It adds n cloth pieces of a new cloth model to a shop. If the shop
	//	already  had  this model, it  increments  the  number  of  pieces.If the  shop  does  not exist, it
	//	ignores the operation.

	List<std::string> lastModels(std::string shop, int n); //It returns a list of the “n” last models added to a given shop in inverse
	//  order.First, it should be the last model added.Second should be the second to the last model,
	//	and so on.If there are not enough last models, it only returns the available ones in this inverse

	List<std::string> shops(); // It returns a list of the shops shorted alphabetically.

	int pieces(std::string shop, std::string model); // It returns the number of pieces of cloth of a given shop and model. It
	//  returns 0 if either the model or the shop does not exist.

	


private:

	SearchTree<std::string, Table<std::string, int>> shopModelMap;

};

#endif



/*
A, B, C
ORDEN: B, A, C
NUEVO ORDEN: C, B, A



*/