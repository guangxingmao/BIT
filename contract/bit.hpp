#define DATA_NAME_LEN (20)
#define MAX_STRING_LEN (256)
#define USER_NAME_LEN (20)
#define TABLE_NAME_LEN (20)
#define TABLE_KEY_LEN (20)
#define CONTRACT_NAME_LEN (20)

extern "C" {
    int upload(); 
    int sell();
    int buy();
    int cancelsell();
    int cancelbuy(); 
    int confirmsell();  
}

//@abi action upload 
struct uploadStruct {
    char   name[DATA_NAME_LEN];
    uint32_t type;
    char   content[MAX_STRING_LEN];   
};

//@abi action sell
struct sellStruct {
    char   name[DATA_NAME_LEN];
    char   description[MAX_STRING_LEN];
    char   accessPolicy[MAX_STRING_LEN];
    uint32_t price;
    uint64_t expiration;    
};

//@abi action buy
struct buyStruct {
    char   name[DATA_NAME_LEN];
    char   owner[USER_NAME_LEN];
};

//@abi action cancelsell
struct cancelsellStruct {
    char   name[DATA_NAME_LEN];
    char   owner[USER_NAME_LEN];
};

//@abi action cancelbuy
struct cancelbuyStruct {
    char   name[DATA_NAME_LEN];
    char   owner[USER_NAME_LEN];
};

//@abi action confirmsell
struct confirmsellStruct {
    char   name[DATA_NAME_LEN];
    char   buyer[USER_NAME_LEN];
    char   encryptedkey[MAX_STRING_LEN];
};

//@abi table dataTable:[index_type:string, key_names:keyName, key_types:string]
struct saveStruct {
    char   name[DATA_NAME_LEN];
    uint32_t type;       //上传资源类型，0:content字段为数据本身，1：content字段为数据索引
    char   content[[USER_NAME_LEN]];
    uint8_t  status;     //资源状态，1：出售中
    uint64_t     uploadTime;
};

//@abi table marketTable:[index_type:string, key_names:keyName, key_types:string]
struct marketStruct {
    char   owner[USER_NAME_LEN];
    char   name[DATA_NAME_LEN];
    uint8_t type;
    uint64_t     expiration;
    char   accessPolicy[MAX_STRING_LEN];
    uint32_t price;
    char   description[MAX_STRING_LEN];
    uint8_t status;
    char buyer[USER_NAME_LEN];
};


