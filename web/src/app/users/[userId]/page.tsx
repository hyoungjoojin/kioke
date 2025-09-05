'use client';

import Dashboard from '@/components/feature/dashboard/Dashboard';
import { ScrollArea } from '@/components/ui/scroll-area';
import { useDashboardQuery } from '@/query/dashboard';
import { useParams } from 'next/navigation';

export default function UserDashboard() {
  const { userId } = useParams<{ userId: string }>();
  const { data: dashboard } = useDashboardQuery({ userId });

  return (
    <ScrollArea className='h-full'>
      <Dashboard dashboard={dashboard} isEditing={false} />
    </ScrollArea>
  );
}
