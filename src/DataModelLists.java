import java.util.EnumMap;
import java.util.Vector;

public final class DataModelLists {

    private static DataModelLists Instance;
    private EnumMap<DataModelListsEnum, Vector<DataModel>> DataModelListsMap;

    private DataModelLists() {
        DataModelListsMap = new EnumMap<>(DataModelListsEnum.class);
    };


    public static DataModelLists getInstance() {
        if (Instance == null)
            Instance = new DataModelLists();
        return Instance;
    }

    public Vector<DataModel> getDataModelList(DataModelListsEnum key) {
        return DataModelListsMap.get(key);
    }

    public void addDataModelList(DataModelListsEnum key, Vector<DataModel> list) {
        DataModelListsMap.put(key, list);
    }

}
