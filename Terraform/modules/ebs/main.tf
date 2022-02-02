
resource "aws_ebs_volume" "volume" {
  availability_zone = "${var.zone_dispo}"
  size              = var.capacite_disque

  tags = {
    Name = "${var.auteur}-ebs"
  }
}