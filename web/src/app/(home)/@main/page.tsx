'use client';

import Dashboard from '@/components/feature/dashboard/Dashboard';
import { ScrollArea } from '@/components/ui/scroll-area';
import { useMyDashboardQuery } from '@/query/dashboard';
import { useDashboard } from '@/store/dashboard';

export default function Home() {
  const { data: dashboard } = useMyDashboardQuery();
  const { isEditing, draft } = useDashboard();

  return (
    <ScrollArea className='h-full'>
      <Dashboard
        dashboard={isEditing ? draft : dashboard}
        isEditing={isEditing}
      />
    </ScrollArea>
  );
}
