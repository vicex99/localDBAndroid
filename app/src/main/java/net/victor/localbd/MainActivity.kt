package net.victor.localbd

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.victor.localbd.Adapter.RecyclerTaskRoomAdapter
import net.victor.localbd.Entity.StringEntity
import net.victor.localbd.Repository.LocalRepository
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), RecyclerTaskRoomAdapter.OnItemClickListener {

    private lateinit var taskList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskList = findViewById(R.id.recyclerView)

        // Set the columns - in this case have only 1
        val layoutManager = GridLayoutManager(this, 1)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        taskList.layoutManager = layoutManager
        taskList.addItemDecoration(dividerItemDecoration)

        LocalRepository.getInstance(application).getAllTask()?.observe(this, Observer { listData ->
            listData?.let { tasks ->
                taskList.adapter = RecyclerTaskRoomAdapter(tasks).also { adapter ->
                    adapter.setOnItemListener(this)
                }
            }
        })
    }

    override fun onItemClick(entity: StringEntity?, position: Int){
        entity?.let {
            Toast.makeText(this, "item: ${entity.name}", Toast.LENGTH_SHORT).show()
        }
    }
}
