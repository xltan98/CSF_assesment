import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { News } from 'src/app/news';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-new',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit{

  @ViewChild('toUpload')
  toUpload!:ElementRef

  form!:FormGroup;
  tagsArray!:FormArray;
  newsId!:string

  fb=inject(FormBuilder)
  nSvc=inject(NewsService)

  ngOnInit(): void {
    this.form=this.createForm()
  }

  addTags(){
    this.tagsArray.push(
      this.fb.group({
        tag:this.fb.control<string>('',[Validators.required])
      })
    )

  }

  createForm(){
    this.tagsArray=this.fb.array([])
    return this.fb.group({
      title: this.fb.control<string>('',[Validators.required]),
      description:this.fb.control<string>('',Validators.required),
      tags:this.tagsArray
    })

  }
  removeLineItem(idx:number){
    
   this.tagsArray.removeAt(idx)
 }

 invalid():boolean{
  return this.form.invalid||this.toUpload.nativeElement.length<0
 }

 onSubmit(){
  console.log(">>>>",this.form.value as News)
  this.nSvc.upload(this.form.value as News,this.toUpload).then(result=>{this.newsId=result.newsId
  console.log(">>>>",this.newsId)
  if(this.newsId.length>0){
  alert(this.newsId)}
  }).catch(error=>
    {alert(error)}
  )
 
 }



}
