'use client';

import Dashboard from '@/components/feature/dashboard/Dashboard';
import { ScrollArea } from '@/components/ui/scroll-area';
import { useMyDashboardQuery } from '@/query/dashboard';
import { useDashboard, useDashboardActions } from '@/store/dashboard';

export default function Home() {
  const { data: dashboard } = useMyDashboardQuery();
  const { isEditing, draft } = useDashboard();
  const { setIsEditingDraft, setDraft } = useDashboardActions();

  if (
    isEditing === false &&
    dashboard &&
    dashboard.widgets &&
    dashboard.widgets.length === 0
  ) {
    return (
      <div className='h-full w-full flex flex-col items-center justify-center text-center text-gray-500'>
        <h2 className='text-2xl font-medium mb-2'>Your dashboard is empty</h2>
        <p>
          Click{' '}
          <span
            onClick={() => {
              setIsEditingDraft(true);
              setDraft(dashboard);
            }}
            className='underline'
          >
            here
          </span>{' '}
          to add widgets and personalize your space.
        </p>
      </div>
    );
  }

  return (
    <ScrollArea className='h-full'>
      <Dashboard
        dashboard={isEditing ? draft : dashboard}
        isEditing={isEditing}
      />
    </ScrollArea>
  );
}
