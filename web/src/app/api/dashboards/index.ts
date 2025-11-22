import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Dashboard, Widget, WidgetType } from '@/types/dashboard';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

async function getMyDashboard(): Promise<Result<Dashboard, KiokeError>> {
  return kioke<{
    widgets: Widget[];
  }>('/dashboards/me', {
    method: HttpMethod.GET,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

interface UpdateDashboardRequestBody {
  widgets: ({
    x: number;
    y: number;
  } & (
    | {
        type: WidgetType.ADD_PAGE;
        content: {
          journalId: string;
        };
      }
    | {
        type: WidgetType.JOURNAL_COVER;
        content: {
          journalId: string;
        };
      }
    | {
        type: WidgetType.WEATHER;
        content: {};
      }
  ))[];
}

async function updateDashboard(options: {
  body: UpdateDashboardRequestBody;
}): Promise<Result<void, KiokeError>> {
  return kioke<void>('/dashboards/me', {
    method: HttpMethod.PATCH,
    body: JSON.stringify(options.body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export { getMyDashboard, updateDashboard };
export type { UpdateDashboardRequestBody };
