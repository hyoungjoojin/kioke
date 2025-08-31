import type {
  NotificationStatus,
  NotificationType,
} from '@/constant/notification';
import type { Role } from '@/constant/role';

export type Notification = {
  id: string;
  status: NotificationStatus;
} & {
  type: NotificationType.SHARE_JOURNAL_REQUEST;
  content: {
    journalId: string;
    journalTitle: string;
    requesterId: string;
    requesterName: string;
    role: Role;
  };
};
