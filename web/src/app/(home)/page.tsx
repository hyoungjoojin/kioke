'use client';

import {
  getMyDashboardQueryKey,
  useGetMyDashboardQuery,
  useUpdateDashboardMutation,
} from '@/app/api/dashboards/query';
import Dashboard from '@/components/feature/dashboard/Dashboard';
import DashboardEditorToolbar from '@/components/feature/dashboard/DashboardEditorToolbar';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { getQueryClient } from '@/lib/query';
import { useDashboard, useDashboardActions } from '@/store/dashboard';

import '@/styles/react-grid-layout.css';

function Home() {
  const { data: dashboard } = useGetMyDashboardQuery();
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
    <Dashboard
      widgets={(isEditing && draft ? draft.widgets : dashboard?.widgets) || []}
      isEditing={isEditing}
    />
  );
}

function HomeTopLeft() {
  const { isEditing } = useDashboard();

  if (isEditing) {
    return <DashboardEditorToolbar />;
  }

  return null;
}

function HomeTopRight() {
  const queryClient = getQueryClient();
  const { isEditing, draft } = useDashboard();
  const { setIsEditingDraft } = useDashboardActions();
  const { mutate: updateDashboard, isPending: isUpdateDashboardPending } =
    useUpdateDashboardMutation();

  if (!isEditing) {
    return (
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <Button variant='icon' icon='ellipsis' />
        </DropdownMenuTrigger>

        <MoreActionsDropdownContent />
      </DropdownMenu>
    );
  }

  const handleSaveButtonClick = () => {
    if (draft) {
      updateDashboard(
        {
          widgets: draft.widgets,
        },
        {
          onSuccess: () => {
            setIsEditingDraft(false);
            queryClient.invalidateQueries({
              queryKey: getMyDashboardQueryKey,
            });
          },
        },
      );
    }
  };

  return (
    <div className='flex items-center gap-2'>
      <Button variant='ghost' onClick={() => setIsEditingDraft(false)}>
        Cancel
      </Button>

      <Button
        onClick={handleSaveButtonClick}
        pending={isUpdateDashboardPending}
      >
        Save
      </Button>
    </div>
  );
}

function MoreActionsDropdownContent() {
  const { setIsEditingDraft, setDraft } = useDashboardActions();
  const { data: dashboard } = useGetMyDashboardQuery();

  return (
    <DropdownMenuContent align='end'>
      {dashboard && (
        <DropdownMenuItem
          icon='edit'
          onClick={() => {
            setIsEditingDraft(true);
            setDraft(dashboard);
          }}
        >
          Edit Dashboard
        </DropdownMenuItem>
      )}
    </DropdownMenuContent>
  );
}

export default Home;
export { HomeTopLeft, HomeTopRight };
