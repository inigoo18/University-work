#ifndef LAWFIRM_H_
#define LAWFIRM_H_

#include "List.h"
#include "Table.h"
#include <string>

class LawFirm {

public:
	LawFirm();
	void addCase(std::string id, std::string name, std::string type, int priority);
	void getCase(std::string id, std::string &name, std::string &type);
	void nextCase(std::string &id, int &priority);
	void removeNextCase();
	bool empty();

	struct law {
		std::string ID;
		std::string name;
		std::string type;
		int priority;
	} tStruct ;

	List<law> cases; // priority 0 first, then 1 then 2.

	Table<std::string, law> getCaseTable;

private:
	int firstPriorityNum;
};

#endif
