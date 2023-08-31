export interface News {
    title:string
    description:string
    tags:tag[]
}

export interface tag{
    tag:string
}

export interface tagCount{
    tag:string
    count:number
}

export interface Newsresp {
    title:string
    description:string
    postDate:number
    image:string
    tags:string[]
}


