import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type {
  NotificationStatus,
  NotificationType,
} from '@/constant/notification';
import type { Notification } from '@/types/notification';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetNotificationsResponse {
  notifications: {
    id: string;
    status: NotificationStatus;
    type: NotificationType;
    content: any;
  }[];
}

function url() {
  return '/notifications';
}

export async function getNotifications(): Promise<
  Result<Notification[], KiokeError>
> {
  return kioke<GetNotificationsResponse>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) => response.map((data) => data.notifications));
}
