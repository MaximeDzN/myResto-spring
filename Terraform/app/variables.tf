variable "zone_dispo" {
  type    = string
  default = "us-east-1"
}

variable "auteur" {
  type    = string
  default = "groupe2"
}

variable "volume_id"{
  type = string
  default = "vol-0c840123584a1acce"
}

variable "utilisateur_ssh" {
  type    = string
  default = "ec2-user"
}

variable "cle_ssh" {
  type    = string
  default = "MyResto"
}


variable "git_projet" {
  type    = string
  default = "myResto-spring"
}

variable "git_proprietaire" {
  type    = string
  default = "MaximeDzN"
}

variable "eip_id" {
  type    = string
  default = "eipalloc-0c3e555f3d6775361"
}

variable "public_ip" {
  type    = string
  default = "3.213.147.10"
}
