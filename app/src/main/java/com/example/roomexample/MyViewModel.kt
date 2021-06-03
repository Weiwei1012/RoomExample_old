package com.example.roomexample


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomexample.database.Scene
import com.example.roomexample.database.SceneDatabaseDao
import kotlinx.coroutines.launch

//viewmodel need to know the database dao (passed argument here) for accessing data from the database
class MyViewModel(dataSource: SceneDatabaseDao) : ViewModel(){
    //get the reference to the database dao
    private val database = dataSource
    //a list of all scenes or matched scenes from the database (LiveData)
    val sceneList = MediatorLiveData<List<Scene>>()
    //get the selected scene (livedata for easy observing)
    val selectedScene = MutableLiveData<Scene>()

   init {
       getAllScenes()
   }

    fun getAllScenes() {  //set the livedata source of the sceneList to be all scenes
        sceneList.addSource(database.loadAllScenes()) { scenes ->
            sceneList.postValue(scenes)
        }
    }

    fun searchScene(name: String) { //set the livedata source of the sceneList to be matched scenes
        sceneList.addSource(database.findScenes(name)) { scenes ->
            sceneList.postValue(scenes)
        }
    }

    fun getScene(sceneId: Long) {  //get the scene from the database by given its id
        viewModelScope.launch {
            selectedScene.value = database.loadOneScene(sceneId)
        }
    }

    fun insertScene(newScene: Scene) {  //add a new scene into the database
        viewModelScope.launch {
            database.insertScene(newScene)
        }
    }

    fun deleteScene(oldScene: Scene) {  //delete a scene from the database
        viewModelScope.launch {
            database.deleteScene(oldScene)
        }
    }

    fun initDB() {  //setup the initial database
        viewModelScope.launch {
            repeat(1) {
                database.insertScene(
                    Scene(
                        "Hualien",
                        "長春祠",
                        "花蓮縣秀林鄉長春祠",
                        R.drawable.photo1_0,
                        "為紀念開闢中橫公路殉職人員所建，祠旁湧泉長年流水成散瀑，公路局取名為「長春飛瀑」，成為中橫公路具特殊意義的地標。"
                    )
                )
                database.insertScene(
                    Scene(
                        "Hualien",
                        "燕子口",
                        "花蓮縣秀林鄉燕子口",
                        R.drawable.photo1_1,
                        "燕子口步道從燕子口到靳珩橋，途中可欣賞太魯閣峽谷、壺穴、湧泉、印地安酋長岩等景觀。"
                    )
                )
                database.insertScene(
                    Scene(
                        "Hualien",
                        "慈母橋",
                        "花蓮縣秀林鄉慈母橋",
                        R.drawable.photo1_2,
                        "慈母橋是一座形狀美麗的紅色大橋，位於天祥以東三公里處的中橫公路上，為立霧溪與其支流荖西溪的匯流處。"
                    )
                )
                database.insertScene(
                    Scene(
                        "Taipei",
                        "天元宮",
                        "新北市淡水無極天元宮",
                        R.drawable.photo0_0,
                        "擁有五層圓型寶塔的壯觀寺廟，每逢櫻花季會吸引大批人潮。"
                    )
                )
                database.insertScene(
                    Scene(
                        "Taipei",
                        "Taipei101",
                        "台北101大樓",
                        R.drawable.photo0_1,
                        "台北101是超高大樓，是綠建築，是購物中心，是觀景台，更是台灣的指標。"
                    )
                )
                database.insertScene(
                    Scene(
                        "Pintong",
                        "墾丁",
                        "屏東墾丁國家公園",
                        R.drawable.photo2_0,
                        "墾丁國家公園是台灣在戰後時期第一個成立的國家公園，成立於1982年。"
                    )
                )
                database.insertScene(
                    Scene(
                        "Pintong",
                        "龍磐公園",
                        "屏東龍磐公園",
                        R.drawable.photo2_1,
                        "未經開發的公園，於開闊的草坪中設有荒野小徑，並坐擁一望無際的海岸風光。"
                    )
                )
            }
        }
    }
}