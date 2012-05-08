import random
import uuid
import json

def dump_data():
	names=("Nullam","Consectetur", "Venenatis", "Vestibulum", "Tellus", "Elit","Quam", "Adipiscing", "Dolor", "Ornare", "Lorem")
	addresses=("Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.",
		"Nulla vitae elit libero, a pharetra augue.",
		"Aenean lacinia bibendum nulla sed consectetur.",
		"Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.",
		"Vestibulum id ligula porta felis euismod semper.")

	clients = []

	for i in range(0,len(names)):
		random_index= random.randrange(0,len(names))
		fullname = names[i] + ' ' +  names[random_index]
		address = addresses[random.randrange(0,len(addresses))]
		phone= '+5052-'+str(random.randint(2200,4000))+''+str(random.randint(100,999))


		clients.append(Client(id=uuid.uuid1(),fullname=fullname, address= address, phone=phone ))

	write_to_disk(json.dumps(clients,default=lambda o: o.__dict__))


def write_to_disk(json):
	print 'Creating mockup file'
	f = open('client.mock','w')
	f.write(json)
	f.close()

class Client:
	def __init__(self, **kwargs):
		self.fullname=kwargs['fullname']
		self.address=kwargs['address']
		self.phone=kwargs['phone']
		self.id=kwargs['id']



if __name__=='__main__':
	dump_data()
