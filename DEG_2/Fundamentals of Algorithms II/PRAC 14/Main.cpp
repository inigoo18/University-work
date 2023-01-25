#include <iostream>
#include "Company.h"
#include "ListToString.h"
using namespace std;

Company company = Company();

bool readCommand() {
	string shop;
	string model;
	int n;
	string cmd;
	cin >> cmd;
	if (cmd == "addShop") {
		cin >> shop;
		company.addShop(shop);
	}
	else if (cmd == "addModel") {
		cin >> shop;
		cin >> model;
		cin >> n;
		company.addModel(shop, model, n);
	}
	else if (cmd == "lastModels") {
		cin >> shop;
		cin >> n;
		List<string> list = company.lastModels(shop, n);
		cout << listToStringCommas(list) << endl;
	}
	else if (cmd == "shops") {
		List<string> list = company.shops();
		cout << listToStringCommas(list) << endl;
	}
	else if (cmd == "pieces") {
		cin >> shop;
		cin >> model;
		int res = company.pieces(shop, model);
		cout << res;
		cout << endl;
	}
	else if (cmd == "end") {
		return false;
	}
	return true;
}


int main() {

	bool OK = true;
	bool OK2 = true;
	while (OK2) {
		while (OK) {
			OK = readCommand();
		}
		company = Company();
		OK2 = readCommand();
		if (OK2) {
			OK = true;
		}
	}
	return 0;
}