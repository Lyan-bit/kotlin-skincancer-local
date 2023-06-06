package com.example.skincancer

import android.content.Context
import java.util.ArrayList
import android.graphics.Bitmap
import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter


class ModelFacade private constructor(context: Context) {

    private var db: DB
    private val assetManager: AssetManager = context.assets
    private var fileSystem: FileAccessor
    private var imageClassifier: ImageClassifier

    private var currentSkinCancer: SkinCancerVO? = null
	private var currentSkinCancers: ArrayList<SkinCancerVO> = ArrayList()

    init {
    	//init
        db = DB(context, null)
        fileSystem = FileAccessor(context)
        imageClassifier = ImageClassifier(context)
	}

    companion object {
        private var instance: ModelFacade? = null
        fun getInstance(context: Context): ModelFacade {
            return instance ?: ModelFacade(context)
        }
    }
    
    fun createSkinCancer(x: SkinCancerVO) { 
			 db.createSkinCancer(x)
			 currentSkinCancer = x
	}
			    
    fun editSkinCancer(x: SkinCancerVO) {
        db.editSkinCancer(x)
        currentSkinCancer = x
	}

	   fun setSelectedSkinCancer(x: SkinCancerVO) {
		    currentSkinCancer = x
	}
		    
		    
	 fun deleteSkinCancer(id: String) {
			 db.deleteSkinCancer(id)
			 currentSkinCancer = null
	 }
			
    fun searchSkinCancer(dates: String) : ArrayList<SkinCancer> {
		val itemsList: ArrayList<SkinCancer> = ArrayList()
			currentSkinCancers = db.listSkinCancer()
			for (x in currentSkinCancers.indices) {
				if ( currentSkinCancers[x].getDates() == dates) {
					val vo: SkinCancerVO = currentSkinCancers[x]
				    val itemx = SkinCancer.createByPKSkinCancer(vo.getId())
					    itemx.id = vo.getId()
				    itemx.dates = vo.getDates()
				    itemx.images = vo.getImages()
				    itemx.outcome = vo.getOutcome()
					itemsList.add(itemx)
				}
			}
			return itemsList
		}
	fun searchSkinCancer(): ArrayList<String> {
		currentSkinCancers = db.listSkinCancer()
		val res: ArrayList<String> = ArrayList()
		for (x in currentSkinCancers.indices) {
			res.add(currentSkinCancers[x].getDates().toString())
		}
		return res
	}
	
    fun imageRecognition(skinCancer: SkinCancer ,images: Bitmap): String {
			val result = imageClassifier.recognizeImage(images)
	        skinCancer.outcome = result[0].title  +": " + result[0].confidence
		    persistSkinCancer(skinCancer)
	    	return result[0].title  +": " + result[0].confidence
		}
			     


	fun listSkinCancer(): ArrayList<SkinCancerVO> {
        currentSkinCancers = db.listSkinCancer()
		
        return currentSkinCancers
	}

	fun listAllSkinCancer(): ArrayList<SkinCancer> {	
		currentSkinCancers = db.listSkinCancer()
		var res = ArrayList<SkinCancer>()
			for (x in currentSkinCancers.indices) {
					val vo: SkinCancerVO = currentSkinCancers[x]
				    val itemx = SkinCancer.createByPKSkinCancer(vo.getId())
	            itemx.id = vo.getId()
            itemx.dates = vo.getDates()
            itemx.images = vo.getImages()
            itemx.outcome = vo.getOutcome()
			res.add(itemx)
		}
		return res
	}

    fun stringListSkinCancer(): ArrayList<String> {
        currentSkinCancers = db.listSkinCancer()
        val res: ArrayList<String> = ArrayList()
        for (x in currentSkinCancers.indices) {
            res.add(currentSkinCancers[x].toString())
        }
        return res
    }

    fun getSkinCancerByPK(value: String): SkinCancer? {
        val res: ArrayList<SkinCancerVO> = db.searchBySkinCancerid(value)
	        return if (res.isEmpty()) {
	            null
	        } else {
	            val vo: SkinCancerVO = res[0]
	            val itemx = SkinCancer.createByPKSkinCancer(value)
            itemx.id = vo.getId()
            itemx.dates = vo.getDates()
            itemx.images = vo.getImages()
            itemx.outcome = vo.getOutcome()
	            itemx
	        }
    }
    
    fun retrieveSkinCancer(value: String): SkinCancer? {
        return getSkinCancerByPK(value)
    }

    fun allSkinCancerIds(): ArrayList<String> {
        currentSkinCancers = db.listSkinCancer()
        val res: ArrayList<String> = ArrayList()
            for (skincancer in currentSkinCancers.indices) {
                res.add(currentSkinCancers[skincancer].getId())
            }
        return res
    }

    fun setSelectedSkinCancer(i: Int) {
        if (i < currentSkinCancers.size) {
            currentSkinCancer = currentSkinCancers[i]
        }
    }

    fun getSelectedSkinCancer(): SkinCancerVO? {
        return currentSkinCancer
    }

    fun persistSkinCancer(x: SkinCancer) {
        val vo = SkinCancerVO(x)
        db.editSkinCancer(vo)
        currentSkinCancer = vo
    }
	

		
}
