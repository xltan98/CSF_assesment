import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { News, Newsresp } from 'src/app/news';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-tagpost',
  templateUrl: './tagpost.component.html',
  styleUrls: ['./tagpost.component.css']
})
export class TagpostComponent implements OnInit{

  nSvc= inject(NewsService)
  activatedRoute= inject(ActivatedRoute)
  ngOnInit(): void {
    this.tag=this.activatedRoute.snapshot.params['tag']

    
  //   this.nSvc.time.subscribe(result=>{this.time=result
  //   console.log("time>>>>",this.time)
  // console.log(">>result>>",result)})
  this.time=this.nSvc.retrieveTime()

  
  //this.nSvc.getArticle(this.time,this.tag).then(result=>{console.log(result)})
  }

  load(){
    this.nSvc.getArticle(this.time,this.tag).then(result=>{console.log(result)
      this.news=result
    })
  }

  time!:number
  tag!:string
  news!:Newsresp[]
  



}
