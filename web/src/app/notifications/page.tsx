'use client';

import { Skeleton } from '@/components/ui/skeleton';
import { NotificationType } from '@/constant/notification';
import { useNotificationsQuery } from '@/query/notification/getNotifications';
import type { Notification } from '@/types/notification';

export default function Notifications() {
  const { data: notifications } = useNotificationsQuery();

  return (
    <>
      <h1 className='text-3xl'>Notifications</h1>
      {notifications ? (
        notifications.map((notification, index) => {
          return <NotificationItem notification={notification} key={index} />;
        })
      ) : (
        <Skeleton />
      )}
    </>
  );
}

function NotificationItem({ notification }: { notification: Notification }) {
  if (notification.type === NotificationType.SHARE_JOURNAL_REQUEST) {
    const content = notification.content;
    return (
      <div>
        <em>{content.requesterName || 'Unknown'}</em> shared
        <em> {content.journalTitle}</em>
      </div>
    );
  }

  return null;
}
