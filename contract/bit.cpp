#include "contractcomm.hpp"
#include "bit.hpp"

#define EXEC_SUCC (0)
#define ERROR_PACK_FAIL (1)
#define ERROR_UNPACK_FAIL (2)
#define ERROR_SAVE_DB_FAIL (3)    
#define ERROR_GET_CONTRACT_NAME_FAIL (4)
#define ERROR_GET_DB_FAIL (5)

static bool unpack_struct(MsgPackCtx *ctx, uploadStruct *info)
{    
    uint32_t size = 0;
    UNPACK_ARRAY(3)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_U32(info, type)
    UNPACK_STR(info, content, (MAX_STRING_LEN + 1))
    
    return true;
}

static bool unpack_struct(MsgPackCtx *ctx, sellStruct *info)
{
    uint32_t size = 0;
    UNPACK_ARRAY(5)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_U32(info, price)
    UNPACK_STR(info, description, (MAX_STRING_LEN + 1))
    UNPACK_STR(info, accessPolicy, (MAX_STRING_LEN + 1))
    UNPACK_U64(info, expiration)

    return true;
}

static bool unpack_struct(MsgPackCtx *ctx, buyStruct *info)
{
    uint32_t size = 0;
    UNPACK_ARRAY(2)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_STR(info, owner, (USER_NAME_LEN + 1))

    return true;
}

static bool unpack_struct(MsgPackCtx *ctx, cancelsellStruct *info)
{
    uint32_t size = 0;
    UNPACK_ARRAY(2)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_STR(info, owner, (USER_NAME_LEN + 1))

    return true;
}

static bool unpack_struct(MsgPackCtx *ctx, cancelbuyStruct *info)
{
    uint32_t size = 0;
    UNPACK_ARRAY(2)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_STR(info, owner, (USER_NAME_LEN + 1))

    return true;
}

static bool unpack_struct(MsgPackCtx *ctx, confirmsellStruct *info)
{
    uint32_t size = 0;
    UNPACK_ARRAY(3)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_STR(info, buyer, (USER_NAME_LEN + 1))
    UNPACK_STR(info, encryptedkey, (MAX_STRING_LEN + 1))

    return true;
}

static bool pack_struct(MsgPackCtx *ctx, sellStruct *info)
{    
    PACK_ARRAY16(5)

    UNPACK_STR(info, name, (DATA_NAME_LEN + 1))
    UNPACK_STR(info, description, (MAX_STRING_LEN + 1)
    UNPACK_STR(info, accessPolicy, (MAX_STRING_LEN + 1)
    UNPACK_U32(info, price)
    UNPACK_U64(info, expiration)
)

    return true;
}

int upload() 
{
    uploadStruct uploadStruct = {{0}};
    saveStruct   saveStruct = {{0}};
    
    if ( !parseParam<uploadStruct>(uploadStruct) )  
        return ERROR_UNPACK_FAIL;

    strcpy(saveStruct.name, uploadStruct.name);
    strcpy(saveStruct.content, uploadStruct.content);
    saveStruct.type = uploadStruct.type;
    saveStruct.statue = 0;
    saveStruct.uploadTime = now();

    if (!saveData<saveStruct>(saveStruct, uploadStruct.owner, uploadStruct.name)) 
        return ERROR_SAVE_DB_FAIL;
    
    return EXEC_SUCC;
}

int sell()
{
    sellStruct sellStruct = {{0}};
    marketStruct marketStruct = {{0}};
    char marketTable[TABLE_NAME_LEN] = "markettable";

    if ( !parseParam<sellStruct>(sellStruct) )
        return ERROR_UNPACK_FAIL;

    //marketStruct.type = sellStruct.type;
    marketStruct.expiration = sellStruct.expiration;
    marketStruct.price = sellStruct.price;
    strcpy(marketStruct.owner, sellStruct.owner);
    strcpy(marketStruct.name, sellStruct.name);
    strcpy(marketStruct.accessPolicy, sellStruct.accessPolicy);
    strcpy(marketStruct.description, sellStruct.description);

    if (!saveData<marketStruct>(marketStruct, marketTable, sellStruct.name)) 
        return ERROR_SAVE_DB_FAIL;

    return EXEC_SUCC;
}

int buy()
{
    buyStruct buyStruct = {{0}};
    marketStruct marketStruct = {{0}};
    char marketTable[TABLE_NAME_LEN] = "markettable";

    char mycontract_name[CONTRACT_NAME_LEN] = "";
    getCtxName(mycontract_name, sizeof(mycontract_name));
    
    if (strlen(mycontract_name) <= 0)
    {
        myprints("ERROR: Get my contract name failed.");
        return ERROR_GET_CONTRACT_NAME_FAIL;
    }

    if ( !parseParam<buyStruct>(buyStruct) )
        return ERROR_UNPACK_FAIL;

    if (!getData<marketStruct>(mycontract_name, marketTable, buyStruct.name, marketStruct) )    {
        myprints("getData failed!");
        return ERROR_GET_DB_FAIL;
    }

    marketStruct.buyer = buyStruct.owner;
    marketStruct.status = 1;

    if (!saveData<marketStruct>(marketStruct, marketTable, buyStruct.name))
        return ERROR_SAVE_DB_FAIL;

    return EXEC_SUCC;
}

int cancelsell()
{
    cancelsellStruct cancelsellStruct = {{0}};
    marketStruct marketStruct = {{0}};
    char marketTable[TABLE_NAME_LEN] = "markettable";

    char mycontract_name[CONTRACT_NAME_LEN] = "";
    getCtxName(mycontract_name, sizeof(mycontract_name));

    if (strlen(mycontract_name) <= 0)
    {
        myprints("ERROR: Get my contract name failed.");
        return ERROR_GET_CONTRACT_NAME_FAIL;
    }

    if ( !parseParam<cancelsellStruct>(cancelsellStruct) )
        return ERROR_UNPACK_FAIL;

    removeBinValue(marketTable, strlen(marketTable), cancelsellStruct.name, strlen(cancelsellStruct.name));

    return EXEC_SUCC;
}

int cancelbuy()
{
    buyStruct buyStruct = {{0}};
    marketStruct marketStruct = {{0}};
    char marketTable[TABLE_NAME_LEN] = "markettable";

    char mycontract_name[CONTRACT_NAME_LEN] = "";
    getCtxName(mycontract_name, sizeof(mycontract_name));

    if (strlen(mycontract_name) <= 0)
    {
        myprints("ERROR: Get my contract name failed.");
        return ERROR_GET_CONTRACT_NAME_FAIL;
    }

    if ( !parseParam<buyStruct>(buyStruct) )
        return ERROR_UNPACK_FAIL;

    if (!getData<marketStruct>(mycontract_name, marketTable, buyStruct.name, marketStruct) )    {
        myprints("getData failed!");
        return ERROR_GET_DB_FAIL;
    }

    marketStruct.buyer = "";
    marketStruct.status = 0;

    if (!saveData<marketStruct>(marketStruct, marketTable, buyStruct.name))
        return ERROR_SAVE_DB_FAIL;

    return EXEC_SUCC;
}

int confirmsell()
{
    confirmsellStruct confirmsellStruct = {{0}};
    marketStruct marketStruct = {{0}}; 
    char marketTable[TABLE_NAME_LEN] = "markettable";
    
    char mycontract_name[CONTRACT_NAME_LEN] = "";
    getCtxName(mycontract_name, sizeof(mycontract_name));
    
    if (strlen(mycontract_name) <= 0)
    {   
        myprints("ERROR: Get my contract name failed.");
        return ERROR_GET_CONTRACT_NAME_FAIL;
    }
    
    if ( !parseParam<confirmsellStruct>(confirmsellStruct) )
        return ERROR_UNPACK_FAIL;
        if (!getData<marketStruct>(mycontract_name, marketTable, confirmsellStruct.name, marketStruct) )    {
        myprints("getData failed!");
        return ERROR_GET_DB_FAIL;
    }
    
    marketStruct.buyer = confirmsellStruct.buyer;
    marketStruct.encryptedkey = confirmsellStruct.encryptedkey;
    
    if (!saveData<marketStruct>(marketStruct, marketTable, confirmsellStruct.name))
        return ERROR_SAVE_DB_FAIL; 

    return EXEC_SUCC;
}
