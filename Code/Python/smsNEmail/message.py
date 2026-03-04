from google.oauth2.credentials import Credentials
from google_auth_oauthlib.flow import InstalledAppFlow
from googleapiclient.discovery import build
from email.mime.text import MIMEText
from google.auth.transport.requests import Request
import base64, os

# --- Scope: send only ---
SCOPES = ['https://www.googleapis.com/auth/gmail.send']

def get_service():
    creds = None
    if os.path.exists('token.json'):
        creds = Credentials.from_authorized_user_file('token.json', SCOPES)
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file('client_secret_730748776317-f5afjm2st6votrgvcjropqp17usctqhr.apps.googleusercontent.com.json', SCOPES)
            creds = flow.run_local_server(port=0)
        with open('token.json', 'w') as token:
            token.write(creds.to_json())
    return build('gmail', 'v1', credentials=creds)

def create_message(sender, to, subject, text):
    msg = MIMEText(text)
    msg['to'] = to
    msg['from'] = sender
    msg['subject'] = subject
    raw = base64.urlsafe_b64encode(msg.as_bytes()).decode()
    return {'raw': raw}

def send_message(service, user_id, message):
    sent = service.users().messages().send(userId=user_id, body=message).execute()
    print('Message sent (ID):', sent['id'])

if __name__ == '__main__':
    service = get_service()
    # message = create_message("pratyushniraula22@gmail.com", "pratyushniraula22@gmail.com",
    #                          "Hello from Raspberry Pi",
    #                          "This email was sent securely with OAuth 2.0!")
    subject = "Hello from Raspberry Pi"
    body = "This email was sent securely with OAuth 2.0!"
    message = create_message("pratyushniraula22@gmail.com", "9173747626@vzwpix.com", subject, body)
    send_message(service, "me", message)
