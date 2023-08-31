import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { tagCount } from 'src/app/news';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.css']
})
export class LandingpageComponent implements OnInit{

  form!:FormGroup
  time!:number
  fb=inject(FormBuilder)
  hSvc=inject(NewsService)
  tcs!:tagCount[]

  ngOnInit(): void {
    this.form=this.createForm()
    
    this.form.get('time')!.valueChanges.subscribe(newTime=>{this.time=newTime;
    this.proccess()
  console.log(this.time)
  this.hSvc.saveTime(this.time)})
    
  }

  createForm(){

    return this.fb.group({
      time:this.fb.control<number>(5,Validators.required)
    })
    
  }

  proccess(){

    
    this.hSvc.getTags(this.time).then(result=>{console.log(">>>>result",this.tcs=result)})

  }

  

}
